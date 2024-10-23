package com.example.doctorapp.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date

object DateConverter {
    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("EEEE, MMMM d, yyyy")
        return format.format(date)
    }
}