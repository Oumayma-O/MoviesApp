package com.example.a9tanya.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.a9tanya.R
import com.example.a9tanya.adapters.FilmListAdapter
import com.example.a9tanya.adapters.MovieItemClickListener
import com.example.a9tanya.adapters.SliderAdapter
import com.example.a9tanya.movieList.data.remote.respond.MovieDto
import com.example.a9tanya.viewModels.MovieViewModel

class MainActivity : AppCompatActivity(), MovieItemClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var recyclerViewPopularMovies: RecyclerView
    private lateinit var recyclerViewUpcomingMovies: RecyclerView
    private lateinit var searchView1: SearchView
    private val slideHandler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.slider)
        recyclerViewPopularMovies = findViewById(R.id.recyclerView1)
        recyclerViewPopularMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewUpcomingMovies = findViewById(R.id.recyclerView2)
        recyclerViewUpcomingMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.nowPlayingMovies.observe(this, Observer { movies ->
            if (movies != null) {
                sliderAdapter = SliderAdapter(movies, viewPager)
                viewPager.adapter = sliderAdapter
                sliderAdapter.attachToViewPager()

                viewPager.clipToPadding = false
                viewPager.clipChildren = false
                viewPager.offscreenPageLimit = 3
                viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(48))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - Math.abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                }
                viewPager.setPageTransformer(compositePageTransformer)

                viewPager.setCurrentItem(1, false)

                viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        slideHandler.removeCallbacksAndMessages(null)
                        slideHandler.postDelayed(sliderRunnable, SliderAdapter.AUTO_SLIDE_INTERVAL)
                    }
                })

                sliderAdapter.startAutoSlide()
            }
        })

        movieViewModel.popularMovies.observe(this, Observer { movies ->
            if (movies != null) {
                recyclerViewPopularMovies.adapter = FilmListAdapter(this@MainActivity, movies, this)
            }
        })

        movieViewModel.upcomingMovies.observe(this, Observer { movies ->
            if (movies != null) {
                recyclerViewUpcomingMovies.adapter = FilmListAdapter(this@MainActivity, movies, this)
            }
        })

        movieViewModel.fetchNowPlayingMovies()
        movieViewModel.fetchPopularMovies()
        movieViewModel.fetchUpcomingMovies()

        searchView1 = findViewById(R.id.searchView1)

        searchView1.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchView1.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                // Show the keyboard as an overlay
                searchView1.isIconified = false
            }
        }

        searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    // Navigate to SearchActivity and pass the query
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    intent.putExtra("SEARCH_QUERY", query)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change if needed
                return true
            }
        })
    }

    private val sliderRunnable = Runnable {
        viewPager.setCurrentItem(viewPager.currentItem + 1, true)
    }

    override fun onMovieClick(movie: MovieDto, movieImageView: ImageView) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("title", movie.title)
        intent.putExtra("imgURL", movie.getFullPosterPath())
        intent.putExtra("imgCover", movie.getFullBackdropPath())
        intent.putExtra("id", movie.id)

        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImageView, "sharedName")
        startActivity(intent, options.toBundle())
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop auto-slide when the activity is destroyed to avoid potential memory leaks
        sliderAdapter.stopAutoSlide()
    }
}
