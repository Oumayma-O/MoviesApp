package com.example.a9tanya.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.a9tanya.R
import com.example.a9tanya.view.welcome.IntroViewPagerAdapter
import com.example.a9tanya.view.welcome.ScreenItem
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var screenPager: ViewPager2
    private lateinit var introViewPagerAdapter: IntroViewPagerAdapter
    private lateinit var wormDotsIndicator: WormDotsIndicator
    private lateinit var btnNext: Button
    private lateinit var btnGetStarted: Button
    private lateinit var btnAnim: Animation
    private lateinit var transAnim: Animation
    private lateinit var Skip: Button
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)


        // when this activity is about to be launched, check if it's opened before or not
        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }

        // hide the action bar
        supportActionBar?.hide()

        // init views
        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        wormDotsIndicator = findViewById(R.id.worm_dots_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.btn_animation)
        Skip = findViewById(R.id.btn_skip)
        transAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_transition)




        // fill list screen
        val mList = ArrayList<ScreenItem>()
        mList.add(ScreenItem("", "", R.drawable.intro))
        mList.add(ScreenItem("Information and trainer", "Learn about any movie and watch the trailer of any movie you like", R.drawable.ic_undraw_home_cinema))
        mList.add(ScreenItem("See movies", "Save your favorite movies to watch later with your friends", R.drawable.ic_undraw_horror_movie))

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        screenPager.adapter = introViewPagerAdapter

        wormDotsIndicator.attachTo(screenPager)

        // next button click listener
        btnNext.setOnClickListener {
            position = screenPager.currentItem
            if (position < mList.size) {
                position++
                screenPager.currentItem = position
            }

            if (position == mList.size - 1) {
                // when we reach the last screen
                loaddLastScreen()
            }
        }


        // Get Started button click listener
        btnGetStarted.setOnClickListener {
            // open main activity
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            // also, we need to save a boolean value to storage so next time when the user runs the app
            // we could know that he has already checked the intro screen activity
            // I'm going to use shared preferences for that process
            //&savePrefsData()
            finish()
        }

        // skip button click listener
        Skip.setOnClickListener { screenPager.currentItem = mList.size }

        // Add a PageChangeCallback to ViewPager2
        screenPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == mList.size - 1) {
                    loaddLastScreen()
                } else {
                    // for screens other than the last one
                    btnNext.visibility = View.VISIBLE
                    btnGetStarted.visibility = View.INVISIBLE
                    Skip.visibility = View.VISIBLE
                    wormDotsIndicator.visibility = View.VISIBLE
                }
            }
        })

    }



    private fun restorePrefData(): Boolean {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        //return pref.getBoolean("isIntroOpnend", false)
        return false
    }

    private fun savePrefsData() {
        val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isIntroOpnend", true)
        editor.apply()
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private fun loaddLastScreen() {
        btnNext.visibility = View.INVISIBLE
        btnGetStarted.visibility = View.VISIBLE
        Skip.visibility = View.INVISIBLE
        wormDotsIndicator.visibility = View.VISIBLE
        // TODO: ADD an animation to the getstarted button
        // setup animation
        btnGetStarted.startAnimation(btnAnim)
    }
}
