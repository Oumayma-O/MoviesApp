package com.example.a9tanya.view.welcome

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a9tanya.R

class IntroViewPagerAdapter(private val mContext: Context, private val mListScreen: List<ScreenItem>) :
    RecyclerView.Adapter<IntroViewPagerAdapter.ScreenViewHolder>() {

    inner class ScreenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSlide: ImageView = itemView.findViewById(R.id.intro_img)
        val title: TextView = itemView.findViewById(R.id.title_0)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val layoutScreen = inflater.inflate(R.layout.layout_screen, parent, false)

        Log.d(ContentValues.TAG, "onCreateViewHolder1 called")
        return ScreenViewHolder(layoutScreen)
    }

    override fun onBindViewHolder(holder: ScreenViewHolder, position: Int) {
        val currentItem = mListScreen[position]

        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.imageSlide.setImageResource(currentItem.screenImg)
    }

    override fun getItemCount(): Int {
        return mListScreen.size
    }
}
