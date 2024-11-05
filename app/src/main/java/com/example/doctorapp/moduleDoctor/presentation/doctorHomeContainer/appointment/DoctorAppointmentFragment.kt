package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R

class DoctorAppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = DoctorAppointmentFragment()
    }

    private val viewModel: DoctorAppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_doctor_appointment, container, false)
    }
}