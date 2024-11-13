package com.example.doctorapp.moduleDoctor.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.databinding.ShiftItemBinding
import com.example.doctorapp.moduleDoctor.presentation.diffUtil.DoctorShiftDiffUtil
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.Define

class DoctorShiftAdapter(
    private val context: Context,
) : ListAdapter<DoctorShift, DoctorShiftAdapter.RegisterShiftViewHolder>(DoctorShiftDiffUtil()) {

    private var onShiftClickListener: OnShiftClickListener? = null
    inner class RegisterShiftViewHolder(private val binding: ShiftItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shift: DoctorShift) {
            binding.apply {
                tvShift.text = String.format(
                    context.getString(R.string.string_date_time_of_shift),
                    DateUtils.convertInstantToTime(shift.startTime),
                    DateUtils.convertInstantToTime(shift.endTime),
                    DateUtils.convertInstantToDate(shift.startTime)
                )
                cbShift.isChecked = shift.isRegistered
                cbShift.setOnCheckedChangeListener { _, _ ->
                    onShiftClickListener?.onShiftClick(shift)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterShiftViewHolder {
        val binding = ShiftItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RegisterShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegisterShiftViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnShiftClickListener {
        fun onShiftClick(shift: DoctorShift)
    }
}