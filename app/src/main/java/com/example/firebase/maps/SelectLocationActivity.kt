package com.example.firebase.maps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.R
import com.example.firebase.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {
    private val selectLocationActivity by lazy { ActivitySelectLocationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(selectLocationActivity.root)

        selectLocationActivity.backArrow.setOnClickListener {
            onBackPressed()
        }
    }
}