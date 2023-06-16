package com.example.happyvet.data.remote

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String? = null,
    val artikel: String? = null,
    val kategori: String? = null,
    val date: String? = null,
    val image: String? = null
) : Parcelable
