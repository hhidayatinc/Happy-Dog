package com.example.happyvet.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.happyvet.R
import com.example.happyvet.databinding.ActivityAddArticleBinding
import com.example.happyvet.ui.viewmodel.AddArticleViewModel
import com.example.happyvet.uriToFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File

class AddArticleActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddArticleBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private val viewModel by viewModels<AddArticleViewModel>()
    private lateinit var getFile: Uri
    private var isError = true
    private var isLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fStore = Firebase.firestore
        auth = Firebase.auth
        setEnable()

        binding.etTitle.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etTitle.error = resources.getString(R.string.empty)
                    isError = true
                }
                else isError = false
                setEnable()
            }

        })

        binding.etArticle.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etArticle.error = resources.getString(R.string.empty)
                    isError = true
                }
                else isError = false
                setEnable()
            }

        })

        binding.etDate.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etDate.error = resources.getString(R.string.empty)
                    isError = true
                }
                else isError = false
                setEnable()
            }

        })

        binding.etKategori.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNullOrEmpty()) {
                    binding.etKategori.error = resources.getString(R.string.empty)
                    isError = true
                }
                else isError = false
                setEnable()
            }

        })

        binding.btnAddPic.setOnClickListener {
            startGallery()
        }

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val article = binding.etArticle.text.toString()
            val date = binding.etDate.text.toString()
            val kategori = binding.etKategori.text.toString()
            viewModel.uploadArticle(title, article, kategori, date, getFile)
            viewModel.stMessage.observe(this){
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    private fun setEnable(){
        binding.btnAdd.isEnabled = isError != true
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddArticleActivity)
                getFile = uri
                binding.imgArticle.setImageURI(uri)
                isLoad = true
            }
        }
    }
}