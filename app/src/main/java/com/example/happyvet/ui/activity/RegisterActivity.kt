package com.example.happyvet.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.happyvet.R
import com.example.happyvet.databinding.RegisterLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity: AppCompatActivity() {
    private lateinit var binding: RegisterLayoutBinding
    private var isError = true
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = RegisterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        fStore = Firebase.firestore
        auth = Firebase.auth
        setEnable()


        binding.etEmail.addTextChangedListener(object: TextWatcher {
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
                setEnable()

            }
        })

        binding.etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etPassword.error = resources.getString(R.string.empty)
                    isError = true
                }
                if(s.toString().length < 8) {
                    binding.etPassword.error = resources.getString(R.string.error_password)
                    isError = true
                }
                else isError = false
                setEnable()

            }
        })

        binding.etNama.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etNama.error = resources.getString(R.string.empty)
                    isError = true
                }
                else isError = false
                setEnable()

            }
        })

        binding.etNomor.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etPassword.error = resources.getString(R.string.empty)
                    isError = true
                }
                else isError = false
                setEnable()
            }
        })

        binding.tvRegister.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener(){
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val nama = binding.etNama.text.toString()
            val nomor = binding.etNomor.text.toString()
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "User Created.", Toast.LENGTH_LONG).show()
                    val userID = auth.currentUser?.uid.toString()
                    val documentReference = fStore.collection("users").document(userID)
                    var user = hashMapOf(
                        "nama" to nama,
                        "email" to email,
                        "nomor" to nomor,
                        "isAdmin" to false,
                        "image" to "https://firebasestorage.googleapis.com/v0/b/happy-vet.appspot.com/o/users%2Fnilpja5Ny6RSUmafnavXDiXtK7e2?alt=media&token=1526d072-f66f-4c6b-8fe8-a88080679c8b"
                    )
                    documentReference.set(user).addOnSuccessListener {
                        Toast.makeText(this, "Berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
                else{
                    Toast.makeText(this, "Error: " + it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }

        }


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

    private fun setEnable(){
        binding.button.isEnabled = isError != true
    }
}