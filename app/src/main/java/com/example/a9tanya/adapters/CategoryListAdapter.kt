package com.example.a9tanya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a9tanya.R
import com.example.a9tanya.movieList.data.remote.respond.movie_detail.Genre

class CategoryListAdapter(private val items: List<Genre>) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context).inflate(R.layout.view_holder_category, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = items[position]
        holder.genreName.text = genre.name

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreName: TextView = itemView.findViewById(R.id.genre_name)
    }
}
