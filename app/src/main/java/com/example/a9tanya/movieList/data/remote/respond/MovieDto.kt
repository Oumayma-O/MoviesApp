package com.example.a9tanya.movieList.data.remote.respond

import com.example.a9tanya.movieList.data.remote.MovieApi

data class MovieDto(
    val adult: Boolean?,
    val backdrop_path: String,
    val genre_ids: List<Int>?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?

){
    fun getFullBackdropPath(): String? {
        return backdrop_path?.let { "${MovieApi.IMAGE_BASE_URL}$it" }
    }

    fun getFullPosterPath(): String? {
        return poster_path?.let { "${MovieApi.IMAGE_BASE_URL}$it" }
    }
}