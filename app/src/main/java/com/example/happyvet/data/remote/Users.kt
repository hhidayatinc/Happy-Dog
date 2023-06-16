package com.example.happyvet.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val userId : String? = null,
    val email: String? = null,
    val isAdmin: Boolean? = null,
    val nama: String? = null,
    val nomor: String? = null,
    val image: String? = null
): Parcelable
