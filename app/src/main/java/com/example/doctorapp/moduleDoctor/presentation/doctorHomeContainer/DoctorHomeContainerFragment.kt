package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
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

    private val viewModel: DoctorHomeContainerViewModel by activityViewModels()
    override fun getVM() = viewModel
    private var navController: NavController? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Log.d("HoangDH", "$viewModel")
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_doctor_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.apply {
            setupWithNavController(navHostFragment.navController)
        }
        Log.d("HoangDH", Prefs.getInstance(requireContext()).accessToken)
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.navigateToDoctorWorking.observe(viewLifecycleOwner) {
            changeTab(it.getContentIfNotHandled() ?: return@observe)
        }
    }

    private fun changeTab(tabId: Int) {
        binding.bottomNav.selectedItemId = tabId
    }

}