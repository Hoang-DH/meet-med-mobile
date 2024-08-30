package com.example.doctorapp.presentation.homeContainer.doctorDetail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDoctorDetailBinding
import com.example.doctorapp.domain.core.base.BaseFragment

class DoctorDetailFragment : BaseFragment<FragmentDoctorDetailBinding, DoctorDetailViewModel>(R.layout.fragment_doctor_detail) {

    companion object {
        fun newInstance() = DoctorDetailFragment()
    }

    private val viewModel: DoctorDetailViewModel by viewModels()
    override fun getVM() = viewModel

    override fun setOnClick() {
        super.setOnClick()
        binding.btnBookAppointment.setOnClickListener {
            showDatePickerDialog()
        }
    }



}