package com.example.happyvet.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.happyvet.databinding.RegisterLayoutBinding

class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: RegisterLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = RegisterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        playAnimation()
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView2, View.TRANSLATION_X, -30f, 30f).apply{
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val editname = ObjectAnimator.ofFloat(binding.etNama, View.ALPHA, 1F).setDuration(150)
        val editnomor = ObjectAnimator.ofFloat(binding.etNomor, View.ALPHA, 1F).setDuration(150)
        val editemail = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1F).setDuration(150)
        val editpaw = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA, 1F).setDuration(150)
        val button = ObjectAnimator.ofFloat(binding.button, View.ALPHA, 1F).setDuration(300)
        val tv = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1F).setDuration(300)
        val tvlogin = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1F).setDuration(300)

        AnimatorSet().apply{
            playSequentially(editname, editnomor, editemail, editpaw, button, tv, tvlogin)
            startDelay = 300
        }.start()
    }
}