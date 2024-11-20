package com.example.doctorapp.modulePatient.presentation.homeContainer

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentHomeContainerBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.constant.Define
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeContainerFragment : BaseFragment<FragmentHomeContainerBinding, HomeContainerViewModel>(R.layout.fragment_home_container) {

    @Inject
    lateinit var appNavigation: AppNavigation

    private lateinit var navController: NavController

    companion object {
        fun newInstance() = HomeContainerFragment()
    }

    private val viewModel: HomeContainerViewModel by viewModels()
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        navController = (childFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController
        binding.bottomNav.apply {
            setupWithNavController(navController)
        }
    }
}