package com.example.shareworldz.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shareworldz.Models.User
import com.example.shareworldz.R
import com.example.shareworldz.SignUpActivity2
import com.example.shareworldz.adaptors.ViewPager
import com.example.shareworldz.databinding.FragmentProfileBinding
import com.example.shareworldz.utils.USER_NODE
import com.example.shareworldz.utils.USER_PROFILE_FOLDER
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPageradapter: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.editProfileButton.setOnClickListener {
            val intent = Intent(activity, SignUpActivity2::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }
        viewPageradapter = ViewPager(requireActivity().supportFragmentManager)
        viewPageradapter.addFragments(MyPostFragment(),"My Posts")
        viewPageradapter.addFragments(MyReelsFragment(),"My Reels")
        binding.profileViewpager.adapter= viewPageradapter
        binding.tabLayout.setupWithViewPager(binding.profileViewpager)
        return binding.root
    }


    companion object {

    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user: User = it.toObject<User>()!!
                binding.name.text = user.name
                binding.bio.text = user.email
                if (!user.image.isNullOrBlank()) {
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}