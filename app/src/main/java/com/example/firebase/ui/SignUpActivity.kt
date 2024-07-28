package com.example.firebase.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.R
import com.example.firebase.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Santhosh 28/7/24
 */
class SignUpActivity : AppCompatActivity() {
    private val signUpActivity by lazy {  ActivitySignUpBinding.inflate(layoutInflater)}
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(signUpActivity.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)
        auth = Firebase.auth
       signUpActivity.btnSignUp.setOnClickListener {
            signUpUser()
        }
       signUpActivity.btnSign.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun signUpUser() {
        val email = signUpActivity.tvUsername.text.toString()
        val number = signUpActivity.tvPhoneNum.text.toString()
        val pass = signUpActivity.tvPassword.text.toString()
        val confirmPassword = signUpActivity.tvConfirmPassword.text.toString()

        // check pass
        if (email.isBlank() || pass.isBlank() || confirmPassword.isBlank() || number.isEmpty()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return
        }
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {success->
            if (success.isSuccessful) {
                Log.i("SignUp", "signUpUserSuccess:$success ")

                Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                finish()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            } else {
                Log.i("SignUp", "signUpUserFailure:$success ")

                Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {error->
            Log.i("SignUp", "signUpUserError:$error ")

        }
    }
}






