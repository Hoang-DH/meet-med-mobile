package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.databinding.AppointmentItemBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.modulePatient.presentation.diffUtil.AppointmentDiffUtil
import com.example.doctorapp.utils.DateUtils

class AppointmentAdapter(private val context: Context) : BaseAdapterLoadMore<BookingShift>(
    AppointmentDiffUtil()
) {
    private var onAppointmentClickListener: OnAppointmentClickListener? = null
    inner class AppointmentViewHolder(private val binding: AppointmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookingShift: BookingShift) {
            binding.apply {
                tvDate.text = bookingShift.timeSlot?.startTime?.let { DateUtils.convertInstantToDate(it, "MMMM d, yyyy - HH:mm") }
                tvDoctorName.text = bookingShift.doctor?.user?.fullName
                tvDepartment.text = bookingShift.doctor?.department?.name
                Glide.with(context)
                    .load(bookingShift.doctor?.user?.imageUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_profile_pic)
                            .error(R.drawable.ic_profile_pic)
                    )
                    .into(ivAvatar)
                btnViewDetail.setOnClickListener {
                    onAppointmentClickListener?.onAppointmentClick(bookingShift)
                }
            }
        }
    }

    override fun onBindViewHolderNormal(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is AppointmentViewHolder) holder.bind(getItem(position))
    }

    override fun onCreateViewHolderNormal(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = AppointmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentViewHolder(binding)
    }

    fun setOnAppointmentClickListener(onAppointmentClickListener: OnAppointmentClickListener) {
        this.onAppointmentClickListener = onAppointmentClickListener
    }

    interface OnAppointmentClickListener {
        fun onAppointmentClick(bookingShift: BookingShift)
    }

}