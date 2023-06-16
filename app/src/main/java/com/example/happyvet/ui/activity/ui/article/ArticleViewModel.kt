package com.example.happyvet.ui.activity.ui.article

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happyvet.data.remote.Article
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private var fStore: FirebaseFirestore = Firebase.firestore
    private var storage: FirebaseStorage = Firebase.storage
    val listArticle = MutableLiveData<Article>()
    val listPict = MutableLiveData<Uri>()
    val user = MutableLiveData<User>()
    val stError = MutableLiveData<String>()
    val isAdmin = MutableLiveData<Boolean>()



    private val _text = MutableLiveData<String>().apply {
        value = "This is article Fragment"
    }
    val text: LiveData<String> = _text

    fun getArticle(){
        fStore.collection("articles").
                addSnapshotListener(object: EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null){
                            Log.e("Firebase error", error.message.toString())
                            return
                        }
                        for (dc: DocumentChange in value?.documentChanges!!){
                            if (dc.type == DocumentChange.Type.ADDED){
                                listArticle.value = dc.document.toObject(Article::class.java)
                            }
                        }
                    }

                })
//        listPict.value?.let {
//            storage.reference.child("articles")
//        }
    }

    fun isUserAdmin(uid: String){
        fStore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {
                isAdmin.value = it.data?.get("isAdmin") as Boolean?
            }
    }
}