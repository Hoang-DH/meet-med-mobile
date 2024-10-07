package com.example.doctorapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.databinding.DepartmentItemBinding
import com.example.doctorapp.presentation.diffUtil.DepartmentDiffUtil

class DepartmentAdapter(
    private val context: Context,
    private var onDepartmentClickListener: ((Department) -> Unit)? = null
) : ListAdapter<Department, DepartmentAdapter.DepartmentViewHolder>(
    DepartmentDiffUtil()
) {

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
                    onDepartmentClickListener?.invoke(department)
                }
            }
        }
    }

    fun setOnDepartmentClickListener(listener: (Department) -> Unit) {
        onDepartmentClickListener = listener
    }
}