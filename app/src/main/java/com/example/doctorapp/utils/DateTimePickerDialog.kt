package com.example.doctorapp.utils

import androidx.fragment.app.FragmentManager
import com.example.doctorapp.R
import com.example.doctorapp.domain.core.base.BaseDialogFragmentNotViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class DateTimePickerDialog private constructor() {

    private var datePicker: MaterialDatePicker<Long>? = null
    private var timePicker: MaterialTimePicker? = null

    init {

        val validDays = listOf(1724112000000, 1724198400000, 1724457600000)

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidator(validDays))
        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setCalendarConstraints(constraintsBuilder.build())
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build()
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setTitleText("Select Appointment time")
            .build()

    }

    fun showDatePickerDialog(
        fragmentManager: FragmentManager
    ) {
        datePicker?.show(fragmentManager, "date_picker")
    }

    companion object {
        private var instance: DateTimePickerDialog? = null

        fun getInstance(): DateTimePickerDialog {
            if (instance == null) {
                instance = DateTimePickerDialog()
            }
            return instance!!
        }
    }


}