package com.example.doctorapp.moduleDoctor.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.data.model.DoctorShift

class DoctorShiftDiffUtil : DiffUtil.ItemCallback<DoctorShift>() {
    override fun areItemsTheSame(oldItem: DoctorShift, newItem: DoctorShift): Boolean {
        return oldItem.id == newItem.id
                && oldItem.startTime == newItem.startTime
                && oldItem.endTime == newItem.endTime
                && oldItem.isRegistered == newItem.isRegistered
    }

    override fun areContentsTheSame(oldItem: DoctorShift, newItem: DoctorShift): Boolean {
        return oldItem.id == newItem.id
                && oldItem.startTime == newItem.startTime
                && oldItem.endTime == newItem.endTime
                && oldItem.isRegistered == newItem.isRegistered
    }
}