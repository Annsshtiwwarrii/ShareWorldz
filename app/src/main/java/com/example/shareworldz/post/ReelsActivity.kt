package com.example.shareworldz.post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shareworldz.HomeActivity
import com.example.shareworldz.Models.Reel
import com.example.shareworldz.Models.User
import com.example.shareworldz.databinding.ActivityReelsBinding
import com.example.shareworldz.utils.REEL
import com.example.shareworldz.utils.REEL_FOLDER
import com.example.shareworldz.utils.USER_NODE
import com.example.shareworldz.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)

    }
    private lateinit var videoUrl: String
    lateinit var progressDialog: ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) { url ->
                if (url != null) {
                    videoUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }
        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user: User = it.toObject<User>()!!
                    val reel: Reel = Reel(videoUrl!!, binding.caption.text.toString(), user.image!!)
                    // Proceed with further actions using the post object
                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL)
                            .document()
                            .set(reel).addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                finish()
                            }


                    }
                }

        }
    }
}