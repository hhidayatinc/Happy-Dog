package com.example.happyvet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.happyvet.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    companion object{
        const val LOADING_TIME = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        auth = Firebase.auth

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            if(auth.currentUser == null) startActivity(Intent(this, LoginActivity::class.java))
            else startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, LOADING_TIME)
    }
}