package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.databinding.CategoryItemBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.DepartmentCategoryDiffUtil

class DepartmentCategoryAdapter(
    private val context: Context,

) : ListAdapter<Department, DepartmentCategoryAdapter.DepartmentCategoryViewHolder>(DepartmentCategoryDiffUtil()) {
    private var selectedPos = 0
    private var onDepartmentClick: OnDepartmentClickListener? = null
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
                    onDepartmentClick?.onDepartmentClick(adapterPosition)
                }
            }
        }
    }

    fun setOnDepartmentClickListener(onDepartmentClickListener: OnDepartmentClickListener) {
        this.onDepartmentClick = onDepartmentClickListener
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

    interface OnDepartmentClickListener {
        fun onDepartmentClick(position: Int)
    }
}