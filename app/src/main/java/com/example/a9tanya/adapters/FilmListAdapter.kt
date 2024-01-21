package com.example.a9tanya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.a9tanya.R
import com.example.a9tanya.movieList.data.remote.respond.MovieDto
import com.example.a9tanya.view.MainActivity

class FilmListAdapter(private val context: Context, private val items: List<MovieDto>, private val listener: MovieItemClickListener) :
    RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.titleText)
        val pic: ImageView = itemView.findViewById(R.id.moviePoster)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMovieClick(items[position], pic)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val requestOptions = RequestOptions()
            .transforms(CenterCrop(), RoundedCorners(30))

        val movie = items[position]

        Glide.with(context)
            .load(movie.getFullPosterPath())
            .apply(requestOptions)
            .placeholder(R.drawable.placeholder)
            .into(holder.pic)

        holder.titleTxt.text = movie.title
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
