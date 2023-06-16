package com.example.happyvet.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.example.happyvet.R
import com.example.happyvet.data.remote.Chat
import com.example.happyvet.data.remote.Users
import com.example.happyvet.databinding.ActivityChatBinding
import com.example.happyvet.ui.activity.fragment.profile.ProfileViewModel
import com.example.happyvet.ui.adapter.ChatAdapter
import com.example.happyvet.ui.viewmodel.AddArticleViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var firebase: FirebaseUser? = null
    var reference: DatabaseReference? = null
    private lateinit var fStore: FirebaseFirestore
    var chatList = ArrayList<Chat>()
    private val viewModel by viewModels<ProfileViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvChat.layoutManager= LinearLayoutManager(this)

        fStore = Firebase.firestore
        var users = intent.getStringExtra("UserId").toString()
        firebase = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("users").child(users)

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.getUser(users)

        viewModel.userData.observe(this){
            binding.imgUser.load(it.image)
            binding.tvName.text = it.nama
        }
//        reference!!.addValueEventListener(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = snapshot.getValue(Users::class.java)
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

        binding.btnSend.setOnClickListener {
            var message: String = binding.etChat.text.toString()

            if(message.isEmpty()){
                Toast.makeText(this, "message is empty", Toast.LENGTH_SHORT).show()
                binding.etChat.setText("")
            }else{
                sendMessage(firebase!!.uid, users, message)
                binding.etChat.setText("")
            }
        }

        getMessage(firebase!!.uid, users)
    }

    fun getMessage(senderId: String, receiverId:String){
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)
                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId) ||
                        chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)
                    ) {
                        chatList.add(chat)
                    }
                }
                val chatAdapter = ChatAdapter(this@ChatActivity, chatList)
                binding.rvChat.adapter = chatAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String){
        var ref: DatabaseReference? = FirebaseDatabase.getInstance().getReference()
        var hashMap: HashMap<String, String> = hashMapOf(
            "senderID" to senderId,
            "receiverId" to receiverId,
            "message" to message
        )
        ref!!.child("Chat").push().setValue(hashMap)
    }
}