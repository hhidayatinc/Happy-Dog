package com.example.happyvet.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happyvet.R
import com.example.happyvet.data.remote.Article

class ArticleAdapter(
    private val articleList: ArrayList<Article>):
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)

        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item: Article = articleList[position]
        holder.title.text = item.title
        holder.kategori.text = item.kategori
        holder.date.text = item.date
    }

    override fun getItemCount(): Int {
        return articleList.size
    }


    public class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val img: ImageView = itemView.findViewById(R.id.img_article)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val kategori: TextView = itemView.findViewById(R.id.tv_kategori)
        val date: TextView = itemView.findViewById(R.id.tv_date)

    }
}