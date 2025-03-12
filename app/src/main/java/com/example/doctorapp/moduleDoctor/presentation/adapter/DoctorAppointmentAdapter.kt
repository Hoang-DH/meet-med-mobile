package com.example.doctorapp.moduleDoctor.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.domain.model.DoctorAppointment
import com.example.doctorapp.domain.model.Notification
import com.example.doctorapp.databinding.DoctorAppointmentItemBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.moduleDoctor.presentation.diffUtil.DoctorAppointmentDiffUtil
import com.example.doctorapp.utils.DateUtils

class DoctorAppointmentAdapter(private val context: Context) : BaseAdapterLoadMore<DoctorAppointment>(
    DoctorAppointmentDiffUtil()
) {

    private var onAppointmentClickListener: OnAppointmentClickListener? = null

    inner class DoctorAppointmentViewHolder(private val binding: DoctorAppointmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctorAppointment: DoctorAppointment) {
            binding.apply {
                Glide.with(context)
                    .load(doctorAppointment.patient?.user?.imageUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_profile_pic)
                            .error(R.drawable.ic_profile_pic)
                            .circleCrop()
                    )
                    .into(ivAvatar)
                tvPatientName.text = doctorAppointment.patient?.user?.fullName
                tvPhoneNumber.text = doctorAppointment.patient?.user?.phone
                tvEmail.text = doctorAppointment.patient?.user?.email
                tvDate.text = doctorAppointment.timeSlot?.startTime?.let { DateUtils.convertInstantToDate(it, "MMMM d, yyyy - HH:mm") }
                tvSymptom.text = String.format(
                    context.getString(R.string.string_symptoms_placeholder),
                    doctorAppointment.symptoms
                )
                root.setOnClickListener {
                    onAppointmentClickListener?.onAppointmentClick(doctorAppointment)
                }
            }
        }
    }

    override fun onBindViewHolderNormal(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is DoctorAppointmentViewHolder) holder.bind(getItem(position))
    }

    override fun onCreateViewHolderNormal(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DoctorAppointmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoctorAppointmentViewHolder(binding)
    }


    fun setOnAppointmentClickListener(appointmentClickListener: OnAppointmentClickListener) {
        onAppointmentClickListener = appointmentClickListener
    }

    interface OnAppointmentClickListener {
        fun onAppointmentClick(doctorAppointment: DoctorAppointment)
    }

}