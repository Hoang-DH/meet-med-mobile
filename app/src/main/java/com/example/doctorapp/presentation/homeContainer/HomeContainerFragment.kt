package com.example.doctorapp.presentation.homeContainer

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.chatapp.domain.core.base.BaseFragment
import com.example.chatapp.domain.core.base.BaseViewModel
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
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.itemIconTintList = null
    }

}