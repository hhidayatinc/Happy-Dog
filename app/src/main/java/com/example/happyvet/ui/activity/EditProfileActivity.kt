package com.example.happyvet.ui.activity

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.widget.Toast
import androidx.activity.viewModels
import com.example.happyvet.R
import com.example.happyvet.databinding.ActivityEditProfileBinding
import com.example.happyvet.ui.viewmodel.AddArticleViewModel
import com.example.happyvet.ui.viewmodel.EditProfileViewModel
import com.example.happyvet.utils.rotateImage
import com.example.happyvet.utils.uriToFile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var fStore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var getFile: Uri
    private val viewModel by viewModels<EditProfileViewModel>()
    private var isAdmin = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        fStore = Firebase.firestore
        storage = Firebase.storage
        val uid = auth.currentUser?.uid.toString()

        viewModel.isUserAdmin(uid)
        viewModel.isAdmin.observe(this){
            isAdmin = it
        }

        binding.buttonCamera.setOnClickListener {
            val intent = Intent(this, CameraxActivity::class.java)
            launchIntentCamera.launch(intent)
        }
        binding.buttonGallery.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
            launchIntentGallery.launch(chooser)
        }
        binding.btnDokter.setOnClickListener {
            viewModel.setAdmin(uid, isAdmin)
            viewModel.stError.observe(this){
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
        }
        binding.button.setOnClickListener{
            viewModel.uploadPhoto(getFile, uid)
            viewModel.stError.observe(this){
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == CAMERA_X_RESULT){
            val myFile = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                it.data?.getSerializableExtra("picture", File::class.java)
            }else{
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? Uri

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile?.let{ file ->
//                rotateImage(file, isBackCamera)
                getFile = file
                binding.imgProfile.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launchIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if(result.resultCode == RESULT_OK){
            val selectImg = result.data?.data as Uri
            selectImg.let{ uri->
                val myFile = uriToFile(uri, this@EditProfileActivity)
                getFile = uri
                binding.imgProfile.setImageURI(uri)
            }
        }
    }

    companion object{
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS= 10
    }
}