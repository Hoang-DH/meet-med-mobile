package com.example.doctorapp.presentation.homeContainer.profile.notification

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentNotificationBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.adapter.NotificationAdapter

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