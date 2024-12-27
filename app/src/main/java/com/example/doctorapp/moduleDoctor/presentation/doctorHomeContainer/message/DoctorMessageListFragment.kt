package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.message

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.databinding.FragmentDoctorMessageListBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.DoctorMessageRoomAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.CheckNetWorkCallback
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorMessageListFragment :
    BaseFragment<FragmentDoctorMessageListBinding, DoctorMessageListViewModel>(R.layout.fragment_doctor_message_list),
    DoctorMessageRoomAdapter.OnMessageRoomClickListener {

    companion object {
        fun newInstance() = DoctorMessageListFragment()
    }


    private val viewModel: DoctorMessageListViewModel by viewModels()
    override fun getVM() = viewModel

    @Inject
    lateinit var appNavigation: AppNavigation

    private var messageRoomAdapter: DoctorMessageRoomAdapter? = null

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
        messageRoomAdapter = DoctorMessageRoomAdapter(context = requireContext())
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
        appNavigation.openDoctorMessageListToDoctorMessageRoomScreen(bundle)
    }
}