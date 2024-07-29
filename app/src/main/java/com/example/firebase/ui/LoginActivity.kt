package com.example.firebase.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.example.firebase.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
/**
 * Santhosh 26/7/24
 */
class LoginActivity : AppCompatActivity() {
    private val loginActivity by lazy {  ActivityLoginBinding.inflate(layoutInflater)}
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginActivity.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)

        auth = FirebaseAuth.getInstance()

        loginActivity.btnLogin.setOnClickListener {
            val email = loginActivity.tvUsername.text.toString()
            val pass = loginActivity.tvPassword.text.toString()
            if (email.isNotEmpty()&&pass.isNotEmpty()){
                login()
            }else{
                Toast.makeText(this, "Enter Login Details", Toast.LENGTH_SHORT).show()
            }

        }
// Check if the user is logged in
        val currentUser = auth.currentUser
        Log.i("LoginActivity", "currentUser:$currentUser ")
        loginActivity.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login() {
        val email = loginActivity.tvUsername.text.toString()
        val pass = loginActivity.tvPassword.text.toString()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Log.i("Login", "loginSuccess:$it ")
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                Log.i("Login", "loginFailure:$it ")

            }
        }.addOnFailureListener{
            Log.i("Login", "loginError:$it ")

        }
    }



}