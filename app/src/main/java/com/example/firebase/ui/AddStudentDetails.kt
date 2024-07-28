package com.example.firebase.ui

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.MainActivity
import com.example.firebase.databinding.ActivityAddDataBinding
import com.example.firebase.maps.FusedLocationService
import com.example.firebase.maps.SelectLocationActivity
import com.example.firebase.model.EmployeeInfo
import com.example.firebase.preference.DataPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

/**
 * Santhosh 27/7/24
 */

class AddStudentDetails : AppCompatActivity() {

        private val addStudentDetails by lazy { ActivityAddDataBinding.inflate(layoutInflater) }
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var databaseReference: DatabaseReference? = null
    private var employeeInfo: EmployeeInfo? = null
    private var dateOfBirth = ""
    private var selectedGender: String = ""
    private var studentClass1: String = ""
    private var studentSection: String = ""
    private val GALLERY_REQUEST_CODE = 12
    private val galleryRequest= 10
    private var lat = ""
    private var long = ""
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {
            addStudentDetails.profileImage.setImageURI(galleryUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addStudentDetails.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)
        genderSelection()
        sectionSelection()
        classSelection()
        datePicker()

        addStudentDetails.backArrow.setOnClickListener {
//            onBackPressed()
            val intent=Intent(this@AddStudentDetails, MainActivity::class.java)
            startActivity(intent)
        }

        addStudentDetails.mapView.setOnClickListener {
            val intent=Intent(this@AddStudentDetails, SelectLocationActivity::class.java)
            startActivity(intent)
        }

        FusedLocationService.latitudeFlow.observe(this) {
            lat = it.latitude.toString()
            long = it.longitude.toString()
            Log.i("TAG", "onCreateTab:$lat ")
            Log.i("TAG", "onCreate:$long ")
        }
        addStudentDetails.profileImage.setOnClickListener {
            picImage()
        }
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("EmployeeInfo")
        employeeInfo = EmployeeInfo()

        addStudentDetails.submit.setOnClickListener(View.OnClickListener {
            val name = addStudentDetails.etName.getText().toString()
            val phone = addStudentDetails.etParentsContact.getText().toString()
            val address = addStudentDetails.etAddress.getText().toString()
            val emergencyContact = addStudentDetails.etEmergencyNumber.toString()
            val bloodGroup = addStudentDetails.etBloodGroup.toString()
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || emergencyContact.isEmpty() || bloodGroup.isEmpty() || studentClass1.isEmpty() || studentSection.isEmpty() || selectedGender.isEmpty() || dateOfBirth.isEmpty()
            ) {
                Toast.makeText(this@AddStudentDetails, "Please add some data", Toast.LENGTH_SHORT).show()
            } else {
                employeeInfo!!.studentName = addStudentDetails.etName.text.toString()
                employeeInfo!!.studentClass = studentClass1
                employeeInfo!!.studentSection = studentSection
                employeeInfo!!.schoolName = addStudentDetails.etSchoolName.text.toString()
                employeeInfo!!.gender = selectedGender
                employeeInfo!!.dateOfBirth = dateOfBirth
                employeeInfo!!.fatherName = addStudentDetails.etFathersName.text.toString()
                employeeInfo!!.matherName = addStudentDetails.etMothersName.text.toString()
                employeeInfo!!.parentContactNumber =
                    addStudentDetails.etParentsContact.text.toString()
                employeeInfo!!.address1 = addStudentDetails.etAddress.text.toString()
                employeeInfo!!.address2 = addStudentDetails.etAddress2.text.toString()
                employeeInfo!!.city = addStudentDetails.etCity.text.toString()
                employeeInfo!!.state = addStudentDetails.etState.text.toString()
                employeeInfo!!.zip = addStudentDetails.etZip.text.toString()
                employeeInfo!!.emergencyContactNumber =
                    addStudentDetails.etEmergencyNumber.text.toString()
                addDataFirebase(employeeInfo!!)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        addStudentDetails.etDob.setOnClickListener {
            val dpd = DatePickerDialog(
                this@AddStudentDetails,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                { _, year, monthOfYear, dayOfMonth ->
                    addStudentDetails.etDob.setText("$dayOfMonth/$monthOfYear/$year")
                    dateOfBirth = ("$dayOfMonth/$monthOfYear/$year").toString()
                },
                year,
                month,
                day
            )
            dpd.window?.setBackgroundDrawableResource(R.color.transparent)
            dpd.show()
        }
    }

    private fun genderSelection() {
        addStudentDetails.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedGender = radioButton.text.toString()
            Log.d("TAG", "genderSelection: $selectedGender")
        }
    }

    private fun sectionSelection() {
        val studentSection1 = listOf("A", "B", "C", "D", "E")
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, studentSection1)
        addStudentDetails.autoSection.setAdapter(adapter)

        addStudentDetails.autoSection.setOnItemClickListener { parent, view, position, id ->
            studentSection = parent.getItemAtPosition(position).toString()

            Log.d("TAG", "classSelection: $studentSection")

        }
    }



    private fun classSelection() {
        val studentClass = listOf("1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th")
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, studentClass)
        addStudentDetails.autoClass.setAdapter(adapter)

        addStudentDetails.autoClass.setOnItemClickListener { parent, view, position, id ->
            studentClass1 = parent.getItemAtPosition(position).toString()
            Log.d("TAG", "classSelection: $studentClass")
        }

    }
    private fun addDataFirebase(employeeInfo: EmployeeInfo) {

        val id: String? = databaseReference?.push()?.getKey()
        employeeInfo.employeeId = id

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val employeeId = databaseReference?.child(userId)?.child("EmployeeInfo")?.push()?.key
            DataPreference(context = this).saveUserData(userId)

//            databaseReference?.child(id)?.setValue((employeeInfo))
            if (id != null) {
                databaseReference?.child(userId)?.child("EmployeeInfo")?.child(employeeId ?: "")
                    ?.setValue(employeeInfo)

                    ?.addOnSuccessListener {
                        // After adding this data we are showing toast message
                        Toast.makeText(
                            this@AddStudentDetails,
                            "Data added successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    ?.addOnFailureListener { error ->
                        // If the data is not added or there is an error
                        Toast.makeText(
                            this@AddStudentDetails,
                            "Failed to add data: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }

        }
    }


    private fun picImage() {
        val option = arrayOf<CharSequence>("take photo", "Select your Gallery")
        val builder = AlertDialog.Builder(this@AddStudentDetails)
        builder.setItems(option) { dialoge, item ->
            if (option[item] == "take photo") {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, GALLERY_REQUEST_CODE)
            } else if (option[item] == "Select your Gallery") {
                galleryLauncher.launch("image/*")
            }
            dialoge.dismiss()
        }
        builder.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val photo: Bitmap? = data?.extras?.get("data") as? Bitmap
                    photo?.let {
                        addStudentDetails.profileImage.setImageBitmap(it)
                    } ?: run {
                        Log.e("TAG", "Camera photo is null")
                    }
                }
                galleryRequest -> {


                }
            }
        } else {
            Log.e("TAG", "Activity result is not OK: resultCode=$resultCode")
        }
    }


}

