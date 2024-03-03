package com.example.shareworldz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shareworldz.Models.Post
import com.example.shareworldz.Models.Reel
import com.example.shareworldz.R
import com.example.shareworldz.adaptors.MyPostRvAdapter
import com.example.shareworldz.adaptors.MyReelAdapter
import com.example.shareworldz.databinding.FragmentMyreelsBinding
import com.example.shareworldz.databinding.FragmentReelsBinding
import com.example.shareworldz.utils.REEL
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyReelsFragment : Fragment() {
private lateinit var binding : FragmentMyreelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyreelsBinding.inflate(inflater,container,false)
        var reelList = ArrayList<Reel>()
        var adapter = MyReelAdapter(requireContext(),reelList )
        binding.rb.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rb.adapter = adapter
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).get().addOnSuccessListener {
            var tempList = arrayListOf<Reel>()
            for (i in it.documents){
                var reel: Reel = i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}