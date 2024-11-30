package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory.details

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R

class DetailAppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = DetailAppointmentFragment()
    }

    private val viewModel: DetailAppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail_appointment, container, false)
    }
}