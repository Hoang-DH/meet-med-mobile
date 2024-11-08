package com.example.doctorapp.utils

import androidx.fragment.app.FragmentManager
import com.example.doctorapp.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class DateTimePickerDialog private constructor(
    fullSlotDay: List<Long>,
    onDateSelectedListener: OnDateTimePickerListener? = null
) {

    private var datePicker: MaterialDatePicker<Long>? = null
    private var timePicker: MaterialTimePicker? = null

    init {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidator(fullSlotDay))
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
        datePicker?.addOnPositiveButtonClickListener { selection ->
            onDateSelectedListener?.onDateTimeSelected(selection)
        }
    }

    fun showDatePickerDialog(
        fragmentManager: FragmentManager
    ) {
        datePicker?.show(fragmentManager, "date_picker")
    }



    companion object {
        private var instance: DateTimePickerDialog? = null

        fun getInstance(fullSlotDay: List<Long>, onDateSelectedListener: OnDateTimePickerListener?): DateTimePickerDialog {
            if (instance == null) {
                instance = DateTimePickerDialog(fullSlotDay, onDateSelectedListener)
            }
            return instance!!
        }
    }

    interface OnDateTimePickerListener {
        fun onDateTimeSelected(date: Long)
    }

}