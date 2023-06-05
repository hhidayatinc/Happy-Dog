package com.example.happyvet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
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
    }

    private fun playAnimation(){
    }

    private fun Login(){

        if (isError == true) binding.button.isEnabled = false
        if (isError == false){
            binding.button.isEnabled = true
            //proses loginnya ...

        }


    }
}