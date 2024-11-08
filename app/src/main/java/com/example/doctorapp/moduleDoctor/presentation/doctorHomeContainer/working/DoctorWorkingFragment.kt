package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDoctorWorkingBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.WorkingPagerAdapter
import com.example.doctorapp.utils.Define
import com.google.android.material.tabs.TabLayoutMediator

class DoctorWorkingFragment : BaseFragment<FragmentDoctorWorkingBinding, DoctorWorkingViewModel>(R.layout.fragment_doctor_working) {

    companion object {
        fun newInstance() = DoctorWorkingFragment()
    }

    private val fragmentList = mutableListOf<Fragment>()

    private val viewModel: DoctorWorkingViewModel by viewModels()
    override fun getVM() = viewModel

    init {
        fragmentList.add(WorkingCategoryFragment.newInstance(Define.WorkingTab.REGISTER_NEW_SHIFT))
        fragmentList.add(WorkingCategoryFragment.newInstance(Define.WorkingTab.MY_SHIFTS))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            vpWorking.adapter = WorkingPagerAdapter(
                this@DoctorWorkingFragment,
                fragmentList,
                resources.getStringArray(R.array.working_tab).toList()
            )
            TabLayoutMediator(tlWorking, vpWorking) { tab, position ->
                tab.text =
                    resources.getStringArray(R.array.working_tab)[position]
            }.attach()
        }
    }

}