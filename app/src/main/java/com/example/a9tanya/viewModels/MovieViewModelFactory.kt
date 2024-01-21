package com.example.a9tanya.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a9tanya.movieList.data.remote.MovieApi

class MovieViewModelFactory(private val movieApi: MovieApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
