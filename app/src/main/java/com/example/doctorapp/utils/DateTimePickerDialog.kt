package com.example.doctorapp.utils

import androidx.fragment.app.FragmentManager
import com.example.doctorapp.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class DateTimePickerDialog private constructor(
    selectedDate: Long,
) {

    private var datePicker: MaterialDatePicker<Long>? = null
    private var mOnDateSelectedListener: OnDateTimePickerListener? = null
    init {

        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(selectedDate)
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build()

        datePicker?.addOnPositiveButtonClickListener { selection ->
            mOnDateSelectedListener?.onDateTimeSelected(selection)
        }
    }

    fun showDatePickerDialog(
        fragmentManager: FragmentManager
    ) {
        datePicker?.show(fragmentManager, "date_picker")
    }

    fun setOnDateTimePickerListener(onDateSelectedListener: OnDateTimePickerListener) {
        mOnDateSelectedListener = onDateSelectedListener
    }

    fun removeOnDateTimePickerListener() {
        mOnDateSelectedListener = null
    }

    companion object {
        private var instance: DateTimePickerDialog? = null

        fun getInstance(selectedDate: Long = MaterialDatePicker.todayInUtcMilliseconds()): DateTimePickerDialog {
            if (instance == null) {
                instance = DateTimePickerDialog(selectedDate)
            }
            return instance!!
        }
    }

    interface OnDateTimePickerListener {
        fun onDateTimeSelected(date: Long)
    }

}