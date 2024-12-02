package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.databinding.FragmentDoctorAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.DoctorAppointmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorAppointmentFragment : BaseFragment<FragmentDoctorAppointmentBinding, DoctorAppointmentViewModel>(R.layout.fragment_doctor_appointment) {

    companion object {
        fun newInstance() = DoctorAppointmentFragment()
    }

    private val viewModel: DoctorAppointmentViewModel by viewModels()
    override fun getVM() = viewModel
    private val fragmentList = mutableListOf<Fragment>()
    init{
        fragmentList.add(DoctorAppointmentCategoryFragment.newInstance(Define.AppointmentTab.UPCOMING))
        fragmentList.add(DoctorAppointmentCategoryFragment.newInstance(Define.AppointmentTab.COMPLETED))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            vpAppointment.adapter = DoctorAppointmentPagerAdapter(
                this@DoctorAppointmentFragment,
                fragmentList,
                resources.getStringArray(R.array.doctor_appointment_tab).toList()
            )
            TabLayoutMediator(tlAppointment, vpAppointment) { tab, position ->
                tab.text =
                    resources.getStringArray(R.array.doctor_appointment_tab)[position]
            }.attach()
        }
    }

    fun changeTab(tab: String) {
        when (tab) {
            Define.AppointmentTab.UPCOMING -> {
                binding.vpAppointment.setCurrentItem(0, true)
            }
            Define.AppointmentTab.COMPLETED -> {
                binding.vpAppointment.setCurrentItem(1, true)
            }
        }
    }

}