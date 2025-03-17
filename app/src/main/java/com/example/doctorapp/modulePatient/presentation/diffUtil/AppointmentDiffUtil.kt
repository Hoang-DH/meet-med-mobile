package com.example.doctorapp.modulePatient.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.domain.model.BookingShift

class AppointmentDiffUtil: DiffUtil.ItemCallback<BookingShift>() {
    override fun areItemsTheSame(oldItem: BookingShift, newItem: BookingShift): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookingShift, newItem: BookingShift): Boolean {
        return oldItem == newItem
    }
}