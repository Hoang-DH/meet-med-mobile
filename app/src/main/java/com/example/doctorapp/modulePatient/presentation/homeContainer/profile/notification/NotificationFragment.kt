package com.example.doctorapp.modulePatient.presentation.homeContainer.profile.notification

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentNotificationBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.NotificationAdapter

class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(R.layout.fragment_notification) {

    companion object {
        fun newInstance() = NotificationFragment()
    }

    private val viewModel: NotificationViewModel by viewModels()
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
}