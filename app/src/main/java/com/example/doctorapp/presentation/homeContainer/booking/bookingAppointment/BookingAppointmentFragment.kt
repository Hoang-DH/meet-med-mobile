package com.example.doctorapp.presentation.homeContainer.booking.bookingAppointment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentBookingAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.utils.DateConverter
import com.example.doctorapp.utils.DateTimePickerDialog
import java.util.Calendar

class BookingAppointmentFragment : BaseFragment<FragmentBookingAppointmentBinding, BookingAppointmentViewModel>(R.layout.fragment_booking_appointment), DateTimePickerDialog.OnDateTimePickerListener {

    companion object {
        fun newInstance() = BookingAppointmentFragment()
    }

    private val viewModel: BookingAppointmentViewModel by viewModels()
    override fun getVM() = viewModel
    private val fullSlotDay = mutableListOf<Long>(1728950400000, 1729209600000, 1729382400000, 1729468800000)
    override fun setOnClick() {
        super.setOnClick()
        binding.tvDate.setOnClickListener {
            DateTimePickerDialog.getInstance(fullSlotDay, this).showDatePickerDialog(childFragmentManager)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            tvDate.text = DateConverter.convertLongToDate(Calendar.getInstance().timeInMillis)
        }
    }

    override fun onDateTimeSelected(date: Long) {
        binding.tvDate.text = DateConverter.convertLongToDate(date)
    }

    // generate time from 8:00 to 17:00
    private fun generateTime(): List<Long> {
        val timeList = mutableListOf<Long>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        timeList.add(calendar.timeInMillis)
        for (i in 1..8) {
            calendar.add(Calendar.HOUR_OF_DAY, 1)
            timeList.add(calendar.timeInMillis)
        }
        return timeList
    }
}