package com.example.a9tanya.adapters

import android.widget.ImageView
import com.example.a9tanya.movieList.data.remote.respond.MovieDto

interface MovieItemClickListener {
    // We will need the ImageView to make the shared animation between the two activities
    fun onMovieClick(movie: MovieDto, movieImageView: ImageView)
}
