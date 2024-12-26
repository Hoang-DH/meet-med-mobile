package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.message

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.data.model.User
import com.example.doctorapp.databinding.FragmentDoctorMessageListBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.MessageRoomAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import javax.inject.Inject

class DoctorMessageListFragment : BaseFragment<FragmentDoctorMessageListBinding, DoctorMessageListViewModel>(R.layout.fragment_doctor_message_list), MessageRoomAdapter.OnMessageRoomClickListener {

    companion object {
        fun newInstance() = DoctorMessageListFragment()
    }

    private val viewModel: DoctorMessageListViewModel by viewModels()
    override fun getVM() = viewModel

    @Inject
    lateinit var appNavigation: AppNavigation

    private var messageRoomAdapter: MessageRoomAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        messageRoomAdapter = MessageRoomAdapter(context = requireContext())
        messageRoomAdapter?.setOnMessageRoomClickListener(this);
        binding.rvMessageList.apply {
            adapter = messageRoomAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }




    override fun onMessageRoomClick(messageRoom: MessageRoom) {
        appNavigation.openMessageListToMessageRoomScreen()
    }
}