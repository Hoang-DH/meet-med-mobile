package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.domain.model.Department
import com.example.doctorapp.databinding.DepartmentItemBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.DepartmentDiffUtil

class DepartmentAdapter(
    private val context: Context,
) : ListAdapter<Department, DepartmentAdapter.DepartmentViewHolder>(
    DepartmentDiffUtil()
) {

    private var onDepartmentClickListener: OnDepartmentClickListener? = null

    fun setOnDepartmentClickListener(listener: OnDepartmentClickListener) {
        onDepartmentClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DepartmentViewHolder {
        val binding = DepartmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DepartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DepartmentViewHolder(private val binding: DepartmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(department: Department) {
            binding.apply {
                Glide.with(context)
                    .load(department.imageUrl)
                    .into(ivDepartment)
                tvDepartment.text = department.name
                itemView.setOnClickListener {
                    onDepartmentClickListener?.onDepartmentClick(department)
                }
            }
        }
    }

    interface OnDepartmentClickListener {
        fun onDepartmentClick(department: Department)
    }
}