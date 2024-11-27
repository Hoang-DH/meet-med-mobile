package com.example.doctorapp.moduleDoctor.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.data.model.DoctorAppointment

class DoctorAppointmentDiffUtil : DiffUtil.ItemCallback<DoctorAppointment>() {
    override fun areItemsTheSame(oldItem: DoctorAppointment, newItem: DoctorAppointment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DoctorAppointment, newItem: DoctorAppointment): Boolean {
        return oldItem == newItem
    }
}