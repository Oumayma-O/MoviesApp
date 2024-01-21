package com.example.a9tanya.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.a9tanya.R
import com.example.a9tanya.movieList.data.remote.respond.MovieDto

class SliderAdapter(private val images: List<MovieDto>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private val handler = Handler(Looper.getMainLooper())
    private val autoSlideRunnable = Runnable {
        viewPager2.currentItem = (viewPager2.currentItem + 1) % itemCount
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewSlider)
        val title :TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slide_item_container, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val requestOptions = RequestOptions()
            .transforms(CenterCrop(), RoundedCorners(30))

        val movie = images[position]
        holder.title.text = movie.title

        Glide.with(holder.itemView.context)
            .load(movie.getFullBackdropPath())
            .apply(requestOptions)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun startAutoSlide(delayMillis: Long) {
        handler.postDelayed(autoSlideRunnable, delayMillis)
    }

    fun stopAutoSlide() {
        handler.removeCallbacks(autoSlideRunnable)
    }

    fun attachToViewPager() {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                startAutoSlide(3000)
            }
        })
    }
}
