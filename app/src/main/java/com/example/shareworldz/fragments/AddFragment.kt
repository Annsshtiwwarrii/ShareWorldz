package com.example.shareworldz.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.shareworldz.databinding.FragmentAddBinding
import com.example.shareworldz.post.PostActivity
import com.example.shareworldz.post.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.post.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
        }
        binding.reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),ReelsActivity::class.java))
        }
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

    }
}