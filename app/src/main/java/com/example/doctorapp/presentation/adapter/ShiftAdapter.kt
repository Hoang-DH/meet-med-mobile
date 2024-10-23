package com.example.doctorapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.data.model.PatientShift
import com.example.doctorapp.databinding.TimeItemBinding

class ShiftAdapter() : ListAdapter<PatientShift, ShiftAdapter.ShiftViewHolder>(ShiftDiffUtil()) {

    inner class ShiftViewHolder(private val binding: TimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shift: PatientShift) {
            binding.apply {
                tvTime.text = buildString {
                    append(shift.startTime.toString())
                    append("-")
                    append(shift.endTime.toString())
                }
                tvSlots.text = shift.slots.toString()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ShiftDiffUtil() : DiffUtil.ItemCallback<PatientShift>() {
        override fun areItemsTheSame(oldItem: PatientShift, newItem: PatientShift): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PatientShift, newItem: PatientShift): Boolean {
            return oldItem == newItem
        }

    }

}