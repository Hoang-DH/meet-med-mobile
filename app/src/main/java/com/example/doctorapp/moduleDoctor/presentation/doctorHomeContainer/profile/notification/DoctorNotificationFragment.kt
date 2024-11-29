package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.profile.notification

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDoctorNotificationBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.NotificationAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorNotificationFragment : BaseFragment<FragmentDoctorNotificationBinding, DoctorNotificationViewModel>(R.layout.fragment_doctor_notification) {

    companion object {
        fun newInstance() = DoctorNotificationFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: DoctorNotificationViewModel by viewModels()
    private val notificationAdapter: NotificationAdapter by lazy { NotificationAdapter(requireContext()) }
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            notificationAdapter.submitList(viewModel.getListNotification())
            rvNotification.adapter = notificationAdapter
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
        }
    }

}