package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.databinding.AppointmentItemBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.AppointmentDiffUtil
import com.example.doctorapp.utils.DateUtils

class AppointmentAdapter(private val context: Context) : ListAdapter<BookingShift, AppointmentAdapter.AppointmentViewHolder>(
    AppointmentDiffUtil()
) {

    inner class AppointmentViewHolder(private val binding: AppointmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookingShift: BookingShift) {
            binding.apply {
                tvDate.text = bookingShift.timeSlot?.startTime?.let { DateUtils.convertInstantToDate(it, "MMMM d, yyyy - hh.mm a") }
                tvDoctorName.text = bookingShift.doctor?.user?.fullName
                tvDepartment.text = bookingShift.doctor?.department?.name
//                Glide.with(context)
//                    .load(bookingShift.doctor?.user?.avatar)
//                    .into(ivDoctorAvatar)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding = AppointmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}