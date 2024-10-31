package com.example.doctorapp.presentation.homeContainer

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentHomeContainerBinding

class HomeContainerFragment : BaseFragment<FragmentHomeContainerBinding, BaseViewModel>(R.layout.fragment_home_container) {

    private lateinit var navController: NavController

    companion object {
        fun newInstance() = HomeContainerFragment()
    }

    private val viewModel: HomeContainerViewModel by viewModels()
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        navController = (childFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).findNavController()
        binding.bottomNav.apply {
            setupWithNavController(navController)

            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.tabHome -> {
                        navController.navigate(R.id.home_fragment)
                        true
                    }

                    R.id.tabProfile -> {
                        navController.navigate(R.id.profile_fragment)
                        true
                    }

                    R.id.tabBooking -> {
                        navController.navigate(R.id.booking_fragment)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }

    }

}