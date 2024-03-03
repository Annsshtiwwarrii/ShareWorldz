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




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding = FragmentAddBinding.inflate(inflater, container,false)
//        return binding.root
        binding.post.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()
        }
        binding.reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),ReelsActivity::class.java))
        }
        // Inflate the layout for this fragment
        return binding.root

    }

    companion object {

    }
}