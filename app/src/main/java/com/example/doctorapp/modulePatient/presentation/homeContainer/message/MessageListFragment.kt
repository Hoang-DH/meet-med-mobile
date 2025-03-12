package com.example.doctorapp.modulePatient.presentation.homeContainer.message

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.model.MessageRoom
import com.example.doctorapp.domain.model.User
import com.example.doctorapp.databinding.FragmentMessageListBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.MessageRoomAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.CheckNetWorkCallback
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MessageListFragment :
    BaseFragment<FragmentMessageListBinding, MessageListViewModel>(R.layout.fragment_message_list), MessageRoomAdapter.OnMessageRoomClickListener {



    companion object {
        fun newInstance() = MessageListFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: MessageListViewModel by viewModels()
    override fun getVM() = viewModel
    private var messageRoomAdapter: MessageRoomAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        checkAndHandleNetworkConnect(object : CheckNetWorkCallback {
            override fun networkConnected() {
                viewModel.getMessageRoomList()
            }

            override fun networkIgnored() {
                TODO("Not yet implemented")
            }
        })

        messageRoomAdapter = MessageRoomAdapter(context = requireContext())
        messageRoomAdapter?.setOnMessageRoomClickListener(this);

        binding.rvMessageList.apply {
            adapter = messageRoomAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.messageRoomList.observe(viewLifecycleOwner){ response ->
            when(response) {
                MyResponse.Loading -> {
                    showHideLoading(true)
                }
                is MyResponse.Success -> {
                    showHideLoading(false)
                    messageRoomAdapter?.submitList(response.data)
                }
                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }
            }
        }
    }


    override fun onMessageRoomClick(messageRoom: MessageRoom) {
        val bundle = Bundle()
        bundle.putParcelable(Define.BundleKey.MESSAGE_ROOM, messageRoom)
        appNavigation.openMessageListToMessageRoomScreen(bundle)
    }
}