package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory.details

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.databinding.FragmentDetailAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAppointmentFragment : BaseFragment<FragmentDetailAppointmentBinding, DetailAppointmentViewModel>(R.layout.fragment_detail_appointment) {

    companion object {
        fun newInstance() = DetailAppointmentFragment()
    }

    private val viewModel: DetailAppointmentViewModel by viewModels()
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadArguments()
    }

    private fun loadArguments() {
        val bundle = arguments
        val appointment = bundle?.getParcelable<BookingShift>(Define.BundleKey.BOOKING_SHIFT)
        val appointmentId = bundle?.getString(Define.BundleKey.APPOINTMENT_ID)
        if(appointmentId != null) {
            viewModel.getAppointmentById(appointmentId)
            viewModel.appointmentResponse.observe(viewLifecycleOwner) { response ->
                when(response) {
                    is MyResponse.Success -> {
                        bindData(response.data)
                    }
                    is MyResponse.Error -> {
                        response.exception.printStackTrace()
                    }
                    else ->{

                    }
                }
            }
        }
        if(appointment != null) {
            bindData(appointment)
        }

    }

    private fun bindData(appointment: BookingShift) {
        binding.apply {
            appointmentDetail.apply {
                btnViewDetail.visibility = View.GONE
                divider2.visibility = View.GONE
                tvDate.text = appointment.timeSlot?.startTime?.let { DateUtils.convertInstantToDate(it, "MMMM d, yyyy - HH:mm") }
                tvDoctorName.text = appointment.doctor?.user?.fullName
                tvDepartment.text = appointment.doctor?.department?.name
                if(appointment.doctor?.yearsOfExperience != null){
                    tvYoe.text = String.format(getString(R.string.string_number_of_yoe), appointment.doctor?.yearsOfExperience)
                    tvYoe.visibility = View.VISIBLE
                    ivStar.visibility = View.VISIBLE
                } else {
                    tvYoe.visibility = View.GONE
                    ivStar.visibility = View.GONE
                }
                context?.let {
                    Glide.with(it)
                        .load(appointment.doctor?.user?.imageUrl)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.ic_profile_pic)
                                .error(R.drawable.ic_profile_pic)
                        )
                        .into(ivAvatar)
                }
            }
            tvSymptom.text = appointment.symptoms
            tvFullName.text = appointment.patient?.user?.fullName
            tvPhoneNumber.text = appointment.patient?.user?.phone
            tvEmail.text = appointment.patient?.user?.email
            tvGender.text = appointment.patient?.user?.gender?.value
            tvAddressLine.text = appointment.patient?.addressLine
            tvDob.text = appointment.patient?.dob
            tvAge.text = appointment.patient?.user?.age.toString()
            tvDistrict.text = appointment.patient?.district
            tvCity.text = appointment.patient?.city
            tvInsurance.text = appointment.patient?.insuranceCode
        }
    }
}