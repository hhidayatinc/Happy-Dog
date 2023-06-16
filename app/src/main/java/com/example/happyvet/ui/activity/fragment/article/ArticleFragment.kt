package com.example.happyvet.ui.activity.fragment.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyvet.data.remote.Article
import com.example.happyvet.databinding.FragmentArticleBinding
import com.example.happyvet.ui.activity.AddArticleActivity
import com.example.happyvet.ui.adapter.ArticleAdapter
import com.example.happyvet.ui.viewmodel.AddArticleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private lateinit var fStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ArticleAdapter
    private lateinit var articleArrayList: ArrayList<Article>
    private var isAdmin = false

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val articleViewModel =
            ViewModelProvider(this).get(ArticleViewModel::class.java)

        val layoutManager = LinearLayoutManager(requireActivity())

        auth = Firebase.auth
        fStore = Firebase.firestore

        val userID = auth.currentUser?.uid.toString()
        val documentReference = fStore.collection("articles").document()
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val user = documentReference.get()

        articleArrayList = arrayListOf()

        articleViewModel.getArticle()

        articleViewModel.listArticle.observe(viewLifecycleOwner){
            articleArrayList.add(it)
            adapter = ArticleAdapter(articleArrayList)
            binding.rvArticle.layoutManager = layoutManager
            binding.rvArticle.setHasFixedSize(true)
            binding.rvArticle.adapter = adapter
        }

        articleViewModel.isUserAdmin(userID)

        articleViewModel.isAdmin.observe(viewLifecycleOwner){
            isAdmin = it
            binding.fabAddArticle.isVisible = isAdmin
        }

        binding.fabAddArticle.setOnClickListener{
            startActivity(Intent(context, AddArticleActivity::class.java))
        }


//        val textView: TextView = binding.textArticle
//        articleViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }



        return root
    }
    }

