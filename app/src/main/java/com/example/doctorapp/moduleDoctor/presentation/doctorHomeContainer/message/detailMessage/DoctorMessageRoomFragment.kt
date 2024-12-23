package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.message.detailMessage

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R

class DoctorMessageRoomFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorMessageRoomFragment()
    }

    private val viewModel: DoctorMessageRoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_doctor_message_room, container, false)
    }
}