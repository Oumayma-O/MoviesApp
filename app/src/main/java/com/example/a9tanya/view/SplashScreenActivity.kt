package com.example.a9tanya.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.a9tanya.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashImg: ImageView
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashImg = findViewById(R.id.splashImg)
        lottieAnimationView = findViewById(R.id.lottie_layer_name)


        Handler().postDelayed({
            splashImg.animate().translationY(-2600f).duration = 1000
            lottieAnimationView.animate().translationY(1400f).duration = 1000

            // Start the OnBoardingActivity after the animation completes
            Handler().postDelayed({
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()  // Optional: Finish the current activity if you don't want to come back to it
            }, 1000)  // Adjust the delay based on your animation duration
        }, 2000)


    }
}
