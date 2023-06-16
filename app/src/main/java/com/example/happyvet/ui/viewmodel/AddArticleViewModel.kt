package com.example.happyvet.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happyvet.data.remote.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File

class AddArticleViewModel: ViewModel() {
    private var fStore: FirebaseFirestore = Firebase.firestore
    private var storage: FirebaseStorage = Firebase.storage
    private var auth: FirebaseAuth = Firebase.auth
    var stMessage = MutableLiveData<String>()


    fun uploadArticle(title: String, artikel: String, kategori: String, date: String, pic: Uri){

        val article= hashMapOf(
            "title" to title,
            "artikel" to artikel,
            "kategori" to kategori,
            "date" to date
        )
        fStore.collection("articles")
            .document()
            .set(article)
            .addOnSuccessListener {
                storage.getReference("articles/$title")
                    .putFile(pic)
                    .addOnSuccessListener {
                        stMessage.value = "Berhasil menambahkan artikel"
                    }
            }
            .addOnFailureListener {
                stMessage.value = it.message.toString()
            }

    }
}