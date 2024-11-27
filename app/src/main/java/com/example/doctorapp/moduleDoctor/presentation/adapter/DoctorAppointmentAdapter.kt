package com.example.doctorapp.moduleDoctor.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.databinding.DoctorAppointmentItemBinding
import com.example.doctorapp.moduleDoctor.presentation.diffUtil.DoctorAppointmentDiffUtil

class DoctorAppointmentAdapter(private val context: Context) : ListAdapter<DoctorAppointment, DoctorAppointmentAdapter.DoctorAppointmentViewHolder>(
    DoctorAppointmentDiffUtil()
) {
    inner class DoctorAppointmentViewHolder(private val binding: DoctorAppointmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctorAppointment: DoctorAppointment) {
            binding.apply {
                tvPatientName.text = doctorAppointment.patientName
                tvPhoneNumber.text = doctorAppointment.phone
                tvEmail.text = doctorAppointment.email
                tvTime.text = String.format(
                    "%s - %s",
                    doctorAppointment.timeSlot?.startTime,
                    doctorAppointment.timeSlot?.endTime
                )
                tvSymptom.text = doctorAppointment.symtom
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAppointmentViewHolder {
        val binding = DoctorAppointmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoctorAppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorAppointmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}