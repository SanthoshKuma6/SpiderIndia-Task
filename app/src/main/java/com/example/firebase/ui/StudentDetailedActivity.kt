package com.example.firebase.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.R
import com.example.firebase.databinding.ActivityStudentDetailedBinding
import com.example.firebase.maps.SelectLocationActivity
import com.example.firebase.model.EmployeeInfo

/**
 * Santhosh 28/7/24
 */
class StudentDetailedActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStudentDetailedBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
        onBackPressed()
        }
        binding.mapView.setOnClickListener {
            val intent= Intent(this@StudentDetailedActivity, SelectLocationActivity::class.java)
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)

        val employee = intent.getSerializableExtra("EXTRA_EMPLOYEE") as EmployeeInfo?
        Log.d("TAG", "onCreate: $employee")

        binding.fatherName.text=employee?.fatherName
        binding.mothername.text=employee?.matherName
        binding.contactNo.text=employee?.parentContactNumber
        binding.emeregencyContactNo.text=employee?.emergencyContactNumber
        binding.address1.text=employee?.address1
        binding.address2.text=employee?.address2
        binding.city.text=employee?.city
        binding.state.text=employee?.state
        binding.zipCode.text=employee?.zip
        binding.gender.text=employee?.gender
        binding.bloodGroup.text=employee?.bloodGroup
        binding.date.text=employee?.dateOfBirth
        binding.tvStudentName.text=employee?.studentName
        binding.tvStudentSchoolName.text=employee?.schoolName

    }



    @Deprecated("Deprecated in Java", ReplaceWith("finishAffinity()"))
    override fun onBackPressed() {
        super.onBackPressed()


    }



}