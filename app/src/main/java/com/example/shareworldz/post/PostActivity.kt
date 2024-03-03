package com.example.shareworldz.post


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shareworldz.HomeActivity
import com.example.shareworldz.Models.Post
import com.example.shareworldz.Models.User
import com.example.shareworldz.databinding.ActivityPostBinding
import com.example.shareworldz.utils.POST
import com.example.shareworldz.utils.POST_FOLDER
import com.example.shareworldz.utils.USER_NODE
import com.example.shareworldz.utils.USER_PROFILE_FOLDER
import com.example.shareworldz.utils.uploadimage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadimage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }
        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }
   //    binding.postButton.setOnClickListener {
//            val post:Post= Post(imageUrl!!,binding.caption.text.toString())
//
//        }
        binding.postButton.setOnClickListener {
            // Check if caption EditText is not null
          //  binding.caption?.let { captionEditText ->
                // Access the text from EditText only if it's not null
                Firebase.firestore.collection(USER_NODE).document().get()
                    .addOnSuccessListener {
                     //   var user = it.toObject<User>()!!
                        val post: Post = Post(
                            postUrl = imageUrl!!,
                            caption = binding.caption.text.toString(),
                            uid = Firebase.auth.currentUser!!.uid,
                            time = System.currentTimeMillis().toString()
                        )
                        // Proceed with further actions using the post object
                        Firebase.firestore.collection(POST).document().set(post)
                            .addOnSuccessListener {
                                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid)
                                    .document()
                                    .set(post).addOnSuccessListener {
                                        startActivity(
                                            Intent(
                                                this@PostActivity,
                                                HomeActivity::class.java
                                            )
                                        )
                                        finish()
                                    }


                            }
                    }
            }
        }
    }
