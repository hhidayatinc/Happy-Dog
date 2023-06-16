package com.example.happyvet.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File

class EditProfileViewModel: ViewModel() {
    private var fStore: FirebaseFirestore = Firebase.firestore
    private var storage: FirebaseStorage = Firebase.storage
    private var auth: FirebaseAuth = Firebase.auth
    val isAdmin = MutableLiveData<Boolean>()
    val stError = MutableLiveData<String>()


    fun uploadPhoto(uri: Uri, uid: String){
        storage.reference.child("users").child(uid).putFile(uri).addOnSuccessListener {
            storage.reference.child("users").child(uid).downloadUrl.addOnSuccessListener {
                fStore.collection("users").document(uid).update("image", it.toString()).addOnSuccessListener {
                    stError.value = "Berhasil mengubah profile"
                }.addOnFailureListener {
                    stError.value = "Error: ${it.message}"
                }
            }
        }


    }

    fun setAdmin(uid: String, isAdmin: Boolean){
        fStore.collection("users").document(uid).update("isAdmin", !isAdmin).addOnSuccessListener {
            stError.value = "Berhasil menjadi dokter"
        }.addOnFailureListener {
            stError.value = "Error: ${it.message}"
        }
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