package com.example.happyvet.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.happyvet.R
import com.example.happyvet.databinding.LoginLayoutBinding

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: LoginLayoutBinding
    private var isError = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = LoginLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.etEmail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etEmail.error = resources.getString(R.string.empty)
                    isError = true
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    binding.etEmail.error = resources.getString(R.string.error_email)
                    isError = true
                }
                else isError = false

            }
        })

        binding.etPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etPassword.error = resources.getString(R.string.empty)
                    isError = true
                }
                if(s.toString().length > 8) {
                    binding.etPassword.error = resources.getString(R.string.error_password)
                    isError = true
                }
                else isError = false

            }
        })

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

        val editemail = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1F).setDuration(150)
        val editpw = ObjectAnimator.ofFloat(binding.etPassword, View.ALPHA, 1F).setDuration(150)
        val button = ObjectAnimator.ofFloat(binding.button, View.ALPHA, 1F).setDuration(300)
        val tv = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1F).setDuration(300)
        val tvregis = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1F).setDuration(300)

        AnimatorSet().apply{
            playSequentially(editemail, editpw, button, tv, tvregis)
            startDelay=300
        }.start()
    }

    private fun Login(){

        if (isError == true) binding.button.isEnabled = false
        if (isError == false){
            binding.button.isEnabled = true
            //proses loginnya ...

        }


    }
}