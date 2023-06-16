package com.example.happyvet.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.happyvet.R
import com.example.happyvet.data.remote.Article
import com.example.happyvet.databinding.ItemArticleBinding
import com.example.happyvet.ui.activity.DetailArticleActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.NonDisposableHandle.parent

class ArticleAdapter(
    private val articleList: ArrayList<Article>):
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        context = parent.context
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                    false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articleList[position]
        holder.binding(item, context)

        holder.itemView.setOnClickListener {
            val detil = Intent(context, DetailArticleActivity::class.java)
            detil.putExtra("title", item.title)
            detil.putExtra("date", item.date)
            detil.putExtra("artikel", item.artikel)
            detil.putExtra("image", item.image)
            context.startActivity(detil)
        }

    }

    override fun getItemCount(): Int {
        return articleList.size
    }


    public class ArticleViewHolder(private val bind: ItemArticleBinding): RecyclerView.ViewHolder(bind.root){

        fun binding(item: Article, context: Context){
            bind.tvTitle.text = item.title
            bind.tvDate.text = item.date
            bind.tvKategori.text = item.kategori
            bind.imgArticle.load(item.image)
        }

    }
}