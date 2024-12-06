package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentMessageRoomBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.MessageAdapter
import com.example.doctorapp.modulePatient.presentation.adapter.MessageRoomAdapter

class MessageRoomFragment : BaseFragment<FragmentMessageRoomBinding, MessageRoomViewModel>(R.layout.fragment_message_room) {

    companion object {
        fun newInstance() = MessageRoomFragment()
    }

    private val viewModel: MessageRoomViewModel by viewModels()
    override fun getVM() = viewModel
    private var messageAdapter: MessageAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        messageAdapter = MessageAdapter(context = requireContext())
        messageAdapter?.submitList(viewModel.messages)
        binding.rvMessageList.apply {
            adapter = messageAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}