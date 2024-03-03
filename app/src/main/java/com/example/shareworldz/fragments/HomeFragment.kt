package com.example.shareworldz.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shareworldz.Models.Post
import com.example.shareworldz.Models.User
import com.example.shareworldz.R
import com.example.shareworldz.adaptors.FollowRvAdapter
import com.example.shareworldz.adaptors.PostAdapter
import com.example.shareworldz.databinding.FragmentHome2Binding
import com.example.shareworldz.utils.FOLLOW
import com.example.shareworldz.utils.POST
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHome2Binding
    private var postList = ArrayList<Post>()
    private lateinit var adapter: PostAdapter
    private var followList = ArrayList<User>()
    private lateinit var followRvAdapter: FollowRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHome2Binding.inflate(inflater, container, false)
        followRvAdapter = FollowRvAdapter(requireContext(),followList)
        adapter = PostAdapter(requireContext(), postList)
        binding.postRv.layoutManager = LinearLayoutManager(requireContext())
        binding.postRv.adapter = adapter
        binding.followRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.followRv.adapter = followRvAdapter
        followRvAdapter = FollowRvAdapter(requireContext(), followList)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get()
            .addOnSuccessListener {
                var tempList = arrayListOf<User>()
                followList.clear()
                for (i in it.documents) {
                    var user: User = i.toObject<User>()!!
                    tempList.add(user)
                }
                followList.addAll(tempList)
                followRvAdapter.notifyDataSetChanged()
            }
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<Post>()
            postList.clear()
            for (i in it.documents) {
                var post: Post = i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}