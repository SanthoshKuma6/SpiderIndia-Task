package com.example.firebase

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityMainBinding
import com.example.firebase.maps.MapsActivity
import com.example.firebase.ui.AddStudentDetails
import com.example.firebase.ui.LoginActivity
import com.example.firebase.ui.ViewData
import com.google.firebase.auth.FirebaseAuth
/**
 * Santhosh 27/7/24
 */
class MainActivity : AppCompatActivity() {
    private val mainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)
        auth = FirebaseAuth.getInstance()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)
        mainBinding.addStudentConstrain.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStudentDetails::class.java)
            startActivity(intent)

        }
        mainBinding.viewStudentConstrain.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewData::class.java)
            startActivity(intent)

        }
        mainBinding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        mainBinding.mapStudentConstrain.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    }

    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()

    }
}

