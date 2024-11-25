package com.example.doctorapp.modulePatient.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.data.model.DoctorBookingShift

class DoctorBookingShiftDiffUtil: DiffUtil.ItemCallback<DoctorBookingShift>() {
    override fun areItemsTheSame(oldItem: DoctorBookingShift, newItem: DoctorBookingShift): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DoctorBookingShift, newItem: DoctorBookingShift): Boolean {
        return oldItem == newItem
    }

}
