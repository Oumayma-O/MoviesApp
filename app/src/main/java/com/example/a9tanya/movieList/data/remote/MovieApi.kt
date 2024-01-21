package com.example.a9tanya.movieList.data.remote

import com.example.a9tanya.movieList.data.remote.respond.cast.CastResponse
import com.example.a9tanya.movieList.data.remote.respond.movie_detail.MovieDetailResponse
import com.example.a9tanya.movieList.data.remote.respond.MovieResponse
import com.example.a9tanya.movieList.data.remote.respond.videos.VideosResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    //category popular upcoming now_playing
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieDetailResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<CastResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideosList(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String= API_KEY
    ): Call<VideosResponse>


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "bbbe4e7ceea0ea40297766e5242f1f2a"
    }
}
