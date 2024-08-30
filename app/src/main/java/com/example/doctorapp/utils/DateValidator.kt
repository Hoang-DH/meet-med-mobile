package com.example.doctorapp.utils

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.android.material.datepicker.CalendarConstraints
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateValidator (private val validDays: List<Long>) : CalendarConstraints.DateValidator {

    override fun isValid(date: Long): Boolean {
        // convert date to day
        return !validDays.contains(date)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(validDays)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateValidator> {
        override fun createFromParcel(parcel: Parcel): DateValidator {
            val validDays = mutableListOf<Long>()
            parcel.readList(validDays, Long::class.java.classLoader)
            return DateValidator(validDays)
        }

        override fun newArray(size: Int): Array<DateValidator?> {
            return arrayOfNulls(size)
        }
    }
}