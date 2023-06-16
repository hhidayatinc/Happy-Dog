package com.example.happyvet.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happyvet.R
import com.example.happyvet.data.remote.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(private val ctx: Context, private val chatList: ArrayList<Chat>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val MESSAGE_LEFT = 0
    private val MESSAGE_RIGHT = 1
    var firebase: FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        if (viewType == MESSAGE_RIGHT) {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_right_chat, parent, false)
            return ViewHolder(view)
        }else{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_left_chat, parent, false)
            return ViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        val chat = chatList[position]
        holder.tvChat.text = chat.message
        //Glide.with(ctx).load(user.image).placeholder(R.drawable.logo_happyvet).into(holder.imgChat)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class ViewHolder(v : View): RecyclerView.ViewHolder(v){
        val tvChat : TextView = v.findViewById(R.id.tv_chat)
        val imgChat : CircleImageView = v.findViewById(R.id.img_chat)
    }

    override fun getItemViewType(position: Int): Int {
        firebase = FirebaseAuth.getInstance().currentUser
        if(chatList[position].senderId == firebase!!.uid){
            return MESSAGE_RIGHT
        }else{
            return MESSAGE_LEFT
        }
    }
}