package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment.detail

import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDetailAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment

class DetailDoctorAppointmentFragment : BaseFragment<FragmentDetailAppointmentBinding, DetailDoctorAppointmentViewModel>(R.layout.fragment_detail_doctor_appointment) {

    companion object {
        fun newInstance() = DetailDoctorAppointmentFragment()
    }

    private val viewModel: DetailDoctorAppointmentViewModel by viewModels()
    override fun getVM() = viewModel

}