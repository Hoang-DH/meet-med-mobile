package com.example.doctorapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateConverter {
    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("EEEE, MMMM d, yyyy")
        return format.format(date)
    }
}