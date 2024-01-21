package com.example.a9tanya.view

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a9tanya.R
import com.example.a9tanya.adapters.MovieItemClickListener
import com.example.a9tanya.adapters.SearchAdapter
import com.example.a9tanya.movieList.data.remote.respond.MovieDto
import com.example.a9tanya.viewModels.MovieViewModel

class SearchActivity : AppCompatActivity(), MovieItemClickListener {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerViewSearch: RecyclerView
    private lateinit var loadingViewSearch: ProgressBar
    private lateinit var listErrorSearch: TextView
    private lateinit var listEmptySearch: TextView
    private lateinit var searchView: SearchView
    private lateinit var searchAdapter: SearchAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val query = intent.getStringExtra("SEARCH_QUERY")

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        if (query != null) {
            movieViewModel.searchMovies(query,1)
        }

        recyclerViewSearch = findViewById(R.id.movieListSearch)

        recyclerViewSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadingViewSearch = findViewById(R.id.loadingViewSearch)
        listErrorSearch = findViewById(R.id.listErrorSearch)
        listEmptySearch = findViewById(R.id.listEmptySearch)
        searchView = findViewById(R.id.searchView)


        movieViewModel.searchResults.observe(this) { searchResults ->
            // Update UI based on searchResults
            // Show loading indicator while API is still fetching results
            loadingViewSearch.visibility = View.VISIBLE

            if (searchResults != null) {
                // Hide loading indicator
                loadingViewSearch.visibility = View.GONE

                if (searchResults.isNotEmpty()) {
                    // Display search results in RecyclerView
                    searchAdapter =
                        SearchAdapter(this, searchResults,this)
                    recyclerViewSearch.adapter = searchAdapter

                    // Make RecyclerView visible
                    recyclerViewSearch.visibility = View.VISIBLE

                    // Hide error message
                    listErrorSearch.visibility = View.GONE

                    // Hide empty list message
                    listEmptySearch.visibility = View.GONE
                } else {
                    // No search results, show empty list message
                    listEmptySearch.visibility = View.VISIBLE

                    // Hide RecyclerView
                    recyclerViewSearch.visibility = View.GONE

                    // Hide error message
                    listErrorSearch.visibility = View.GONE
                }
            } else {
                // Show error message if searchResults is null
                listErrorSearch.visibility = View.VISIBLE

                // Hide RecyclerView
                recyclerViewSearch.visibility = View.GONE

                // Hide loading indicator
                loadingViewSearch.visibility = View.GONE

                // Hide empty list message
                listEmptySearch.visibility = View.GONE
            }
        }

        // Set up the SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    // Trigger search when user submits the query
                    movieViewModel.searchMovies(query, 1)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    movieViewModel.searchMovies(newText, 1)
                }
                return true
            }
        })
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



}


