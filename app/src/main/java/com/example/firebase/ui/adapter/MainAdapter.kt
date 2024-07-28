package com.example.firebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.databinding.EmployeeItemBinding
import com.example.firebase.model.EmployeeInfo

/**
 * Santhosh 27/7/24
 */
class MainAdapter(
    private var employees: List<EmployeeInfo>,
    private val onItemClick: (EmployeeInfo) -> Unit,
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        holder.bind(employee, onItemClick)
    }

    override fun getItemCount(): Int = employees.size

    fun updateData(newEmployees: List<EmployeeInfo>) {
        val diffCallback = EmployeeDiffCallback(employees, newEmployees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        employees = newEmployees
        diffResult.dispatchUpdatesTo(this)
    }
}

class EmployeeViewHolder(private val binding: EmployeeItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        employee: EmployeeInfo,
        onItemClick: (EmployeeInfo) -> Unit,
    ) {
        binding.tvName.text = employee.studentName
        binding.tvSchoolName.text = employee.parentContactNumber
        binding.parentConstrain.setOnClickListener {
            onItemClick(employee)
        }
    }
}

class EmployeeDiffCallback(
    private val oldList: List<EmployeeInfo>,
    private val newList: List<EmployeeInfo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].employeeId == newList[newItemPosition].employeeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
