package com.example.happyvet.ui.activity.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.happyvet.databinding.FragmentArticleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private lateinit var fStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val articleViewModel =
            ViewModelProvider(this).get(ArticleViewModel::class.java)

        auth = Firebase.auth
        fStore = Firebase.firestore
        val userID = auth.currentUser?.uid.toString()
        val documentReference = fStore.collection("users").document(userID)
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val user = documentReference.get()

//        val textView: TextView = binding.textArticle
//        articleViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }



        return root
    }
    }

