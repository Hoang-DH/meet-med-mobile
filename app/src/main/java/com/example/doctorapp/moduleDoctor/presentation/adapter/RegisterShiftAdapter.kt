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
import com.example.doctorapp.presentation.utils.DateUtils

class RegisterShiftAdapter(private val context: Context): ListAdapter<DoctorShift, RegisterShiftAdapter.RegisterShiftViewHolder>(DoctorShiftDiffUtil()) {

    inner class RegisterShiftViewHolder(private val binding: ShiftItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(shift: DoctorShift) {
          binding.apply {
              tvShift.text = String.format(
                  context.getString(R.string.string_date_time_of_shift),
                  DateUtils.convertInstantToTime(shift.startTime),
                  DateUtils.convertInstantToTime(shift.endTime),
                  DateUtils.convertInstantToDate(shift.startTime))
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

}