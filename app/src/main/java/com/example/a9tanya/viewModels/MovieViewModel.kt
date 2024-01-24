package com.example.a9tanya.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a9tanya.movieList.data.remote.MovieApi
import com.example.a9tanya.movieList.data.remote.respond.MovieDto
import com.example.a9tanya.movieList.data.remote.RetrofitHelper.movieApi
import com.example.a9tanya.movieList.data.remote.respond.MovieResponse
import com.example.a9tanya.movieList.data.remote.respond.cast.Cast
import com.example.a9tanya.movieList.data.remote.respond.cast.CastResponse
import com.example.a9tanya.movieList.data.remote.respond.movie_detail.MovieDetailResponse
import com.example.a9tanya.movieList.data.remote.respond.videos.VideoDto
import com.example.a9tanya.movieList.data.remote.respond.videos.VideosResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieViewModel : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<List<MovieDto>>()
    val nowPlayingMovies: LiveData<List<MovieDto>> get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<List<MovieDto>>()
    val popularMovies: LiveData<List<MovieDto>> get() = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<MovieDto>>()
    val upcomingMovies: LiveData<List<MovieDto>> get() = _upcomingMovies

    private val _credits = MutableLiveData<CastResponse?>()
    val credits: LiveData<CastResponse?> get() = _credits

    private val _videos = MutableLiveData<List<VideoDto>>()
    val videos: LiveData<List<VideoDto>> get() = _videos

    private val _movieDetail = MutableLiveData<MovieDetailResponse?>()
    val movieDetail: LiveData<MovieDetailResponse?> get() = _movieDetail

    private val _similarMovies = MutableLiveData<List<MovieDto>>()
    val similarMovies: LiveData<List<MovieDto>> get() = _similarMovies

    private val _searchResults = MutableLiveData<List<MovieDto>>()
    val searchResults: LiveData<List<MovieDto>> get() = _searchResults

    private val _networkError = MutableLiveData<String>()
    val networkError: LiveData<String> get() = _networkError


    fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val response = movieApi.getMoviesList("now_playing", 1)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _nowPlayingMovies.value = movies
                    Log.d(TAG, "Now Playing Movies fetched successfully: $movies")
                } else {
                    // Handle error
                    _networkError.value = "Error fetching Now Playing Movies"
                    _nowPlayingMovies.value = emptyList()
                    Log.e(TAG, "Error fetching Now Playing Movies: ${response.code()} - ${response.message()}")
                    Log.e(TAG, "Error response body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _networkError.value = "Erreur réseau: ${e.message}"
                Log.e(TAG, "Exception during Now Playing Movies fetch: ${e.message}")
            }
        }
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val response = movieApi.getMoviesList("popular", 1)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _popularMovies.value = movies
                    Log.d(TAG, "Popular Movies fetched successfully: $movies")
                } else {
                    // Handle error
                    _networkError.value = "Error fetching Popular Movies"

                    _popularMovies.value = emptyList()
                    Log.e(TAG, "Error fetching Popular Movies: ${response.message()}")
                }
            } catch (e: Exception) {
                _networkError.value = "Erreur réseau: ${e.message}"
                Log.e(TAG, "Exception during Popular Movies fetch: ${e.message}")
            }
        }
    }

    fun fetchUpcomingMovies() {
        viewModelScope.launch {
            try {
                val response = movieApi.getMoviesList("upcoming", 1)
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    _upcomingMovies.value = movies
                    Log.d(TAG, "Upcoming Movies fetched successfully: $movies")
                } else {
                    // Handle error
                    _networkError.value = "Error fetching Upcoming Movies"
                    _upcomingMovies.value = emptyList()
                    Log.e(TAG, "Error fetching Upcoming Movies: ${response.message()}")
                }
            } catch (e: Exception) {
                _networkError.value = "Erreur réseau: ${e.message}"
                Log.e(TAG, "Exception during Upcoming Movies fetch: ${e.message}")
            }
        }
    }


    fun fetchMovieDetail(movieId: Int) {
        val call = movieApi.getMovieDetail(movieId)

        call.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    _movieDetail.value = response.body()
                } else {
                    _networkError.value = "Failed to fetch movie details"
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                _networkError.value = "Erreur réseau: ${t.message}"

            }
        })
    }


    fun fetchMovieCredits(movieId: Int) {
        val call = movieApi.getMovieCredits(movieId)

        call.enqueue(object : Callback<CastResponse> {
            override fun onResponse(call: Call<CastResponse>, response: Response<CastResponse>) {
                if (response.isSuccessful) {
                    _credits.value = response.body()
                } else {
                    _networkError.value = "Failed to fetch movie credits"
                }
            }

            override fun onFailure(call: Call<CastResponse>, t: Throwable) {
                _networkError.value = "Erreur réseau: ${t.message}"

            }
        })
    }



    fun fetchVideos(movieId: Int) {
        val call = movieApi.getVideosList(movieId)

        call.enqueue(object : Callback<VideosResponse> {
            override fun onResponse(call: Call<VideosResponse>, response: Response<VideosResponse>) {
                if (response.isSuccessful) {
                    _videos.value = response.body()?.results
                } else {
                    _networkError.value = "Failed to fetch videos"

                }
            }

            override fun onFailure(call: Call<VideosResponse>, t: Throwable) {
                _networkError.value = "Erreur réseau: ${t.message}"
            }
        })
    }




    fun fetchSimilarMovies(movieId: Int) {
        val call = movieApi.getSimilarMovies(movieId)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    _similarMovies.value = response.body()?.results
                } else {
                    _networkError.value = "Failed to fetch similar movies"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _networkError.value = "Network error: ${t.message}"
            }
        })
    }


    fun searchMovies(query: String, page: Int) {
        val call = movieApi.searchMovie(query, page)

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    _searchResults.value = response.body()?.results
                } else {
                    _networkError.value = "Failed to fetch search results"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _networkError.value = "Network error: ${t.message}"
            }
        })
    }


    fun refreshDataMain() {
        viewModelScope.launch {
            fetchNowPlayingMovies()
            fetchPopularMovies()
            fetchUpcomingMovies()
        }

    }


    fun refreshData(id: Int) {
        viewModelScope.launch {
            fetchVideos(id)
            fetchSimilarMovies(id)
            fetchMovieCredits(id)
            fetchMovieDetail(id)
        }
    }






    companion object {
        private const val TAG = "MovieViewModel"
    }
}
