package com.example.firebase.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.example.firebase.databinding.ActivitySplashBinding
import com.example.firebase.preference.DataPreference

/**
 * Santhosh 26/7/24
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashScreen by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(splashScreen.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        val backgroundImage: TextView = splashScreen.slideLeft
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.image_slider)
        backgroundImage.startAnimation(slideAnimation)
        login()
    }

    private fun login() {
        val userId = DataPreference(this).getUserDetails()["user_id"]
        if (userId=="null" || userId.isNullOrBlank()) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}