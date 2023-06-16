package com.example.happyvet.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.happyvet.R
import com.example.happyvet.data.remote.Users
import com.example.happyvet.ui.activity.ChatActivity
import de.hdodenhof.circleimageview.CircleImageView

class HomeAdapter(private val ctx: Context, private val userList: ArrayList<Users>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val user = userList[position]
        holder.tvName.text = user.nama
        Glide.with(ctx).load(user.image).placeholder(R.drawable.logo_happyvet).into(holder.imgChat)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("UserId", user.userId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(v : View): RecyclerView.ViewHolder(v){
        val tvName : TextView = v.findViewById(R.id.tv_name)
        val tvRole : TextView = v.findViewById(R.id.tv_role)
        val imgChat : CircleImageView = v.findViewById(R.id.img_chat)
    }
}