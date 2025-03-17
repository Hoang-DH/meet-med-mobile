package com.example.doctorapp.modulePatient.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.domain.model.Department

class DepartmentDiffUtil : DiffUtil.ItemCallback<Department>() {
    override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
        return oldItem == newItem
    }
}