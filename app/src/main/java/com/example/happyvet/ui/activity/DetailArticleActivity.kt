package com.example.happyvet.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.happyvet.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val title = intent.getStringExtra("title").toString()
        val date = intent.getStringExtra("date").toString()
        val artikel = intent.getStringExtra("artikel").toString()
        val image = intent.getStringExtra("image").toString()

        binding.tvTitle.text = title
        binding.tvDate.text = date
        binding.tvArticle.text = artikel
        binding.imgArticle.load(image)



    }
}