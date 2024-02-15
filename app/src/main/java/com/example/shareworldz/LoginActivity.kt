package com.example.shareworldz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shareworldz.Models.User
import com.example.shareworldz.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.loginBtn.setOnClickListener {
            if (binding.Email.editText?.text.toString().equals("") or
                binding.Password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@LoginActivity,
                    "please fill all the details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                var user = User(
                    binding.Email.editText?.text.toString(),
                    binding.Password.editText?.text.toString()
                )
                Firebase.auth.signInWithEmailAndPassword(user.email!!, user.passwoed!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
startActivity(Intent(this@LoginActivity, HomeActivity::class.java ))
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}