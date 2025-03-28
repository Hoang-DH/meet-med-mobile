package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDoctorWorkingBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.WorkingPagerAdapter
import com.example.doctorapp.constant.Define
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        loadArguments()
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

    private fun loadArguments() {
        arguments?.let {
            val isFrom = it.getString(Define.BundleKey.IS_FROM)
            if(isFrom == "notification"){
                Handler().postDelayed({
                    changeTab(Define.WorkingTab.MY_SHIFTS)
                }, 50)
            }
        }
    }

    // change tab
    fun changeTab(tab: String) {
        when (tab) {
            Define.WorkingTab.REGISTER_NEW_SHIFT -> {
                binding.vpWorking.setCurrentItem(0, true)
            }
            Define.WorkingTab.MY_SHIFTS -> {
                binding.vpWorking.setCurrentItem(1, true)
            }
        }
    }

}