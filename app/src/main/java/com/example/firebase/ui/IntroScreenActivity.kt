package com.example.firebase.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.R
import com.example.firebase.databinding.ActivityIntroScreenBinding
/**
 * Santhosh 26/7/24
 */
class IntroScreenActivity : AppCompatActivity() {
    private val introScreenActivity by lazy {  ActivityIntroScreenBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(introScreenActivity.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)
        introScreenActivity.btnLogin.setOnClickListener {
            val intent= Intent(this@IntroScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        introScreenActivity.btnSignup.setOnClickListener {
            val intent= Intent(this@IntroScreenActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}