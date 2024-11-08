package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDoctorHomeContainerBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.utils.Prefs

class DoctorHomeContainerFragment : BaseFragment<FragmentDoctorHomeContainerBinding, DoctorHomeContainerViewModel>(R.layout.fragment_doctor_home_container) {

    companion object {
        fun newInstance() = DoctorHomeContainerFragment()
    }

    private val viewModel: DoctorHomeContainerViewModel by viewModels()
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_doctor_container) as NavHostFragment
        binding.bottomNav.apply {
            setupWithNavController(navHostFragment.navController)
        }
    }
}