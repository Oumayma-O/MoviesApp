package com.example.a9tanya.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.a9tanya.R
import com.example.a9tanya.adapters.ActorsListAdapter
import com.example.a9tanya.adapters.CategoryListAdapter
import com.example.a9tanya.adapters.FilmListAdapter
import com.example.a9tanya.adapters.MovieItemClickListener
import com.example.a9tanya.adapters.VideosAdapter
import com.example.a9tanya.movieList.data.remote.respond.MovieDto
import com.example.a9tanya.viewModels.MovieViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator


class DetailsActivity :  AppCompatActivity(), MovieItemClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerViewSimilairMovies: RecyclerView
    private lateinit var recyclerViewCredits: RecyclerView
    private lateinit var recyclerViewGenres: RecyclerView
    private lateinit var viewPagerVideos: ViewPager2
    private lateinit var dotsIndicator: DotsIndicator



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        recyclerViewSimilairMovies = findViewById(R.id.SimilairMoviesRecyclerview)

        recyclerViewSimilairMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewGenres = findViewById(R.id.genresRecyclerView)

        recyclerViewGenres.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewCredits = findViewById(R.id.creditsRecyclerView)

        recyclerViewCredits.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewPagerVideos = findViewById(R.id.videosViewPager)

        dotsIndicator = findViewById(R.id.dots_indicator)




        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]




        val title = intent.getStringExtra("title")
        val imgURL = intent.getStringExtra("imgURL")
        val imgCover = intent.getStringExtra("imgCover")
        val id = intent.getIntExtra("id",0)
        Log.d("id", "id: $id")

        movieViewModel.fetchVideos(id)

        movieViewModel.videos.observe(this) { videos ->

            if (videos != null) {
                if (videos.isNotEmpty()) {
                    val videosAdapter = VideosAdapter(videos)
                    viewPagerVideos.adapter = videosAdapter
                    dotsIndicator.attachTo(viewPagerVideos)

                    findViewById<TextView>(R.id.EmptyListText).visibility = View.GONE
                    findViewById<ConstraintLayout>(R.id.constraintLayout1).visibility = View.VISIBLE
                } else {
                    findViewById<TextView>(R.id.EmptyListText).visibility = View.VISIBLE
                    findViewById<ConstraintLayout>(R.id.constraintLayout1).visibility = View.GONE
                }
            } else {
                val message = "No videos available for this movie."
                Log.e("DetailsActivity", message)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }

        }


        movieViewModel.fetchSimilarMovies(id)


        movieViewModel.similarMovies.observe(this) { movies ->
            if (movies != null) {
                if (movies.isNotEmpty()) {
                    Log.d("DetailsActivity", "Similar Movies Response: $movies")

                    recyclerViewSimilairMovies.adapter =


                        FilmListAdapter(this@DetailsActivity, movies, this)
                }else {
                    findViewById<TextView>(R.id.EmptyListSimilair).visibility = View.VISIBLE
                    findViewById<ConstraintLayout>(R.id.constraintLayout3).visibility = View.GONE

                }

                }
        }


        movieViewModel.fetchMovieCredits(id)


        movieViewModel.credits.observe(this) { credits ->
            if (credits != null) {
                if (credits.cast != null) {
                    Log.d("DetailsActivity", "Similar Movies Response: ${credits.cast}")

                    recyclerViewCredits.adapter =
                        ActorsListAdapter(this@DetailsActivity,credits.cast)
                }
            }
        }




        val titleTextView = findViewById<TextView>(R.id.detail_movie_title)
        titleTextView.text = title

        val movieImageView = findViewById<ImageView>(R.id.detail_movie_img)
        Glide.with(this)
            .load(imgURL)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(movieImageView)

        val backdropImageView = findViewById<ImageView>(R.id.detail_movie_cover)
        Glide.with(this)
            .load(imgCover)
            .placeholder(R.drawable.placeholder)
            .into(backdropImageView)

        if (id != null) {
            movieViewModel.fetchMovieDetail(id)
        }

        movieViewModel.movieDetail.observe(this) { movieDetailResponse ->
            movieDetailResponse?.let {
                Log.d("DetailsActivity", "Movie Detail Response: $it")

                recyclerViewGenres.adapter =
                    CategoryListAdapter(it.genres)

                val taglineTextView = findViewById<TextView>(R.id.tagline)
                taglineTextView.text = it.tagline

                val releaseDateTextView = findViewById<TextView>(R.id.release_date)
                releaseDateTextView.text = it.release_date

                val ratingTextView = findViewById<TextView>(R.id.rating)
                ratingTextView.text = it.vote_average.toString()

                val runtimeTextView = findViewById<TextView>(R.id.runtime)
                val runtime = "${it.runtime} min"
                runtimeTextView.text = runtime

                val descriptionTextView = findViewById<TextView>(R.id.MovieDetailDescription)
                descriptionTextView.text = it.overview

                val backButton = findViewById<FloatingActionButton>(R.id.back)
                backButton.setOnClickListener {
                    // Set up the same transition animation
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this@DetailsActivity,
                        movieImageView,
                        "sharedName"
                    )
                    startActivity(Intent(this@DetailsActivity, MainActivity::class.java), options.toBundle())
                    onBackPressed()
                }


            }
        }



    }

    override fun onMovieClick(movie: MovieDto, movieImageView: ImageView) {
        val intent = Intent(this@DetailsActivity, DetailsActivity::class.java)
        intent.putExtra("title", movie.title)
        intent.putExtra("imgURL", movie.getFullPosterPath())
        intent.putExtra("imgCover", movie.getFullBackdropPath())
        intent.putExtra("id", movie.id)

        val options =
            ActivityOptions.makeSceneTransitionAnimation(this@DetailsActivity, movieImageView, "sharedName")
        startActivity(intent, options.toBundle())
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
