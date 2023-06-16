package com.example.happyvet.ui.activity.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.happyvet.R
import com.example.happyvet.data.remote.Users
import com.example.happyvet.databinding.FragmentProfileBinding
import com.example.happyvet.ui.activity.EditProfileActivity
import com.example.happyvet.ui.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var user: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        user = FirebaseAuth.getInstance()
        fStore = Firebase.firestore
        val uid = user.currentUser?.uid.toString()


        dashboardViewModel.getUser(uid)

        dashboardViewModel.userData.observe(viewLifecycleOwner){
            binding.tvProfile.text = it.nama

            binding.imgProfile.load(it.image)
        }

        binding.button.setOnClickListener{
            logOut()
        }

        binding.editProfile.setOnClickListener {
            val intent = Intent(getActivity(), EditProfileActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        return root
    }

    private fun logOut(){
        val ad = getActivity()?.let { AlertDialog.Builder(it) }
        ad?.setTitle(getString(R.string.logout_confirm))
            ?.setPositiveButton(getString(R.string.yes)){ _, _ ->
                user.signOut()
                val intent = Intent(getActivity(), LoginActivity::class.java)
                getActivity()?.startActivity(intent)
                activity?.finish()
            }
            ?.setNegativeButton(getString(R.string.no), null)
        val alert = ad?.create()
        alert?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}