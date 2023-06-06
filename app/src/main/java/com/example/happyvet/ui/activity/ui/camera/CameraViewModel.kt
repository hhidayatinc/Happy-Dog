package com.example.happyvet.ui.activity.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is capture Fragment"
    }
    val text: LiveData<String> = _text
}