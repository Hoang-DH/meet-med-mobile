package com.example.doctorapp.modulePatient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.data.model.DoctorBookingShift
import com.example.doctorapp.databinding.BookingDateItemBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.DoctorBookingShiftDiffUtil
import com.example.doctorapp.utils.DateUtils

class BookingShiftAdapter(
) :
    ListAdapter<DoctorBookingShift, BookingShiftAdapter.BookingShiftViewHolder>(
        DoctorBookingShiftDiffUtil()
    ) {
    private var onShiftClick: OnShiftClick? = null
    inner class BookingShiftViewHolder(private val binding: BookingDateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookingShift: DoctorBookingShift) {
            binding.apply {
                tvDate.text =
                    bookingShift.shift?.startTime?.let { DateUtils.convertInstantToDate(it) }
                tvDayOfWeek.text =
                    bookingShift.shift?.startTime?.let { DateUtils.convertInstantToDayOfWeek(it) }
                tvDate.setOnClickListener {
                    onShiftClick?.onShiftClick(bookingShift)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingShiftViewHolder {
        val binding = BookingDateItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingShiftViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnShiftClick(onShiftClick: OnShiftClick) {
        this.onShiftClick = onShiftClick
    }

    interface OnShiftClick {
        fun onShiftClick(bookingShift: DoctorBookingShift)
    }

}