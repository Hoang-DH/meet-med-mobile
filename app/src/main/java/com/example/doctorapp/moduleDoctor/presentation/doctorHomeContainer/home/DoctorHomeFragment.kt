package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.data.model.User
import com.example.doctorapp.databinding.FragmentDoctorHomeBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.utils.Prefs

class DoctorHomeFragment : BaseFragment<FragmentDoctorHomeBinding, DoctorHomeViewModel>(R.layout.fragment_doctor_home) {

    companion object {
        fun newInstance() = DoctorHomeFragment()
    }

    private val viewModel: DoctorHomeViewModel by viewModels()
    override fun getVM() = viewModel
    private var user: User? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        user = Prefs.getInstance(requireContext()).user
        binding.apply {
            tvName.text = String.format(getString(R.string.string_doctor_name), user?.fullName)
        }
    }
}