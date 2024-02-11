package com.example.shareworldz

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.shareworldz.Models.User
import com.example.shareworldz.databinding.ActivitySignUp2Binding
import com.example.shareworldz.utils.USER_NODE
import com.example.shareworldz.utils.USER_PROFILE_FOLDER
import com.example.shareworldz.utils.uploadimage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUpActivity2 : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUp2Binding.inflate(layoutInflater)
    }
    lateinit var user: User

    // for add image->
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
             uploadimage(uri, USER_PROFILE_FOLDER){
if (user==null){

}else{
    user.image= it
    binding.profileImage.setImageURI(uri)
}
            }
        }
    }

    // for log in password and email
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = User()
        binding.signup.setOnClickListener {
            if (binding.textField.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Please fill the all imformation",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.name = binding.textField.editText?.text.toString()
                        user.passwoed = binding.password.editText?.text.toString()
                        user.email = binding.email.editText?.toString().toString()
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
startActivity(Intent(this@SignUpActivity2,HomeActivity::class.java))
                            }
                    } else {
                        Toast.makeText(
                            this@SignUpActivity2,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
    }
}