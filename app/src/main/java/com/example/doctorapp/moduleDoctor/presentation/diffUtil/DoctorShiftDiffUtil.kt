package com.example.doctorapp.moduleDoctor.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.domain.model.DoctorShift
import com.example.doctorapp.domain.model.DoctorShifts

class DoctorShiftDiffUtil : DiffUtil.ItemCallback<DoctorShifts>() {
    override fun areItemsTheSame(oldItem: DoctorShifts, newItem: DoctorShifts): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DoctorShifts, newItem: DoctorShifts): Boolean {
        return oldItem == newItem
    }
}