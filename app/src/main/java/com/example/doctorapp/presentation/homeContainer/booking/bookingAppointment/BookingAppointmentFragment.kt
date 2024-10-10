package com.example.doctorapp.presentation.homeContainer.booking.bookingAppointment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R

class BookingAppointmentFragment : Fragment() {

    companion object {
        fun newInstance() = BookingAppointmentFragment()
    }

    private val viewModel: BookingAppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_booking_appointment, container, false)
    }
}