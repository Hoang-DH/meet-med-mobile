package com.example.doctorapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.databinding.CategoryItemBinding
import com.example.doctorapp.presentation.diffUtil.DepartmentCategoryDiffUtil

class DepartmentCategoryAdapter(
    private val context: Context,
    private val onDepartmentClick: ((Int) -> Unit)? = null
) : ListAdapter<Department, DepartmentCategoryAdapter.DepartmentCategoryViewHolder>(DepartmentCategoryDiffUtil()) {
    private var selectedPos = 0

    inner class DepartmentCategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(department: Department) {
            binding.apply {
                tvDepartment.text = department.name
                if (selectedPos == adapterPosition) {
                    tvDepartment.setTextColor(context.resources.getColor(R.color.white, null))
                } else {
                    tvDepartment.setTextColor(context.resources.getColor(R.color.color_1C2A3A, null))
                }
                root.isSelected = selectedPos == adapterPosition
                root.setOnClickListener {
                    notifyItemChanged(selectedPos)
                    selectedPos = adapterPosition
                    notifyItemChanged(selectedPos)
                    onDepartmentClick?.invoke(adapterPosition)
                }
            }
        }
    }

    fun setSelectedDepartment(position: Int) {
        val selectedPos = position
        notifyItemChanged(selectedPos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentCategoryViewHolder {
        val binding = CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DepartmentCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}