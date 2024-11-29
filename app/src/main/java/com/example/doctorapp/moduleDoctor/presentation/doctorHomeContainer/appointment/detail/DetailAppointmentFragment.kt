package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDetailAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment

class DetailAppointmentFragment : BaseFragment<FragmentDetailAppointmentBinding, DetailAppointmentViewModel>(R.layout.fragment_detail_appointment) {

    companion object {
        fun newInstance() = DetailAppointmentFragment()
    }

    private val viewModel: DetailAppointmentViewModel by viewModels()
    override fun getVM() = viewModel

}