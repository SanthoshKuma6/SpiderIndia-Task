package com.example.firebase.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.R
import com.example.firebase.databinding.ActivityDashboardBinding
import com.example.firebase.model.EmployeeInfo
import com.example.firebase.ui.adapter.MainAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Santhosh 27/7/24
 */
class ViewData : AppCompatActivity() {
    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }
    private lateinit var adapter: MainAdapter
    private var databaseReference: DatabaseReference? = null
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.black,this.theme)
        binding.backArrow.setOnClickListener { onBackPressed() }
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("EmployeeInfo")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MainAdapter(emptyList()) { item ->
            Log.d("TAG", "onCreate: setOnClickListener $item")
            val intent = Intent(this, StudentDetailedActivity::class.java)
            intent.putExtra("EXTRA_EMPLOYEE", item)
            startActivity(intent)

        }
        binding.recyclerView.adapter = adapter
        getData()

    }


    private fun getData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            databaseReference?.child(userId)?.child("EmployeeInfo")
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val employeeList = mutableListOf<EmployeeInfo>()
                        for (snapshot in dataSnapshot.children) {
                            val employee = snapshot.getValue(EmployeeInfo::class.java)
                            if (employee != null) {
                                employeeList.add(employee)
                            }
                        }
                        adapter.updateData(employeeList)

                        Log.d("TAG", "Employee List: $employeeList")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("TAG", "Failed to read value.", error.toException())
                    }
                })
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

}