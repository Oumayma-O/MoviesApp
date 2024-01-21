package com.example.a9tanya.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.recyclerview.widget.RecyclerView
import com.example.a9tanya.R
import com.example.a9tanya.movieList.data.remote.respond.videos.VideoDto

class VideosAdapter(private val videos: List<VideoDto>) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bindVideo(video)
    }

    override fun getItemCount(): Int = videos.size

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoWebView: WebView = itemView.findViewById(R.id.videoWebView)

        init {
            // Move WebView settings to the constructor or an appropriate place
            videoWebView.settings.javaScriptEnabled = true
            videoWebView.settings.loadWithOverviewMode = true
            videoWebView.settings.useWideViewPort = true
        }

        fun bindVideo(video: VideoDto) {
            val videoKey = video.key
            val videoUrl = "https://www.youtube.com/embed/$videoKey"

            val html = "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>"
            videoWebView.loadData(html, "text/html", "utf-8")
        }
    }
}
