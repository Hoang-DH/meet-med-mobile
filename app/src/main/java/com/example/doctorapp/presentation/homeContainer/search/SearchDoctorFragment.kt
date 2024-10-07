package com.example.doctorapp.presentation.homeContainer.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSearchDoctorBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchDoctorFragment : BaseFragment<FragmentSearchDoctorBinding, SearchDoctorViewModel>(R.layout.fragment_search_doctor) {

    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        fun newInstance() = SearchDoctorFragment()
    }

    private val viewModel: SearchDoctorViewModel by viewModels()
    override fun getVM() = viewModel

    override fun bindingAction() {
        super.bindingAction()
        binding.ivBack.setOnClickListener{
            appNavigation.openSearchDoctorToHomeContainerScreen()
        }
    }
}