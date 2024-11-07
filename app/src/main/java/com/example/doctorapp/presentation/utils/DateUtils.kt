package com.example.doctorapp.presentation.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

object DateUtils {

    private val format = SimpleDateFormat("EEEE, MMMM d, yyyy, HH:mm", java.util.Locale.getDefault()).apply {
        timeZone = Calendar.getInstance().timeZone
    }

    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        return format.format(date)
    }


    fun convertInstantToTime(time: Instant): String {
        val formatterTime = DateTimeFormatter.ofPattern("hh:mm a")
        val dateTime = LocalDateTime.ofInstant(time, ZoneId.systemDefault())
        return formatterTime.format(dateTime)
    }

    fun convertInstantToDate(time: Instant): String {
        val formatterDate = DateTimeFormatter.ofPattern("EEEE, dd-MMMM-yyyy")
        val dateTime = LocalDateTime.ofInstant(time, ZoneId.systemDefault())
        return formatterDate.format(dateTime)
    }

    fun checkDateIsSameDay(date1: Long, date2: Long): Boolean {
        return format.format(Date(date1)) == format.format(Date(date2))
    }

    fun isToday(date: Long): Boolean {
        val today = Calendar.getInstance().time
        return format.format(Date(date)) == format.format(today)
    }

    fun isYesterday(date: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val yesterday = calendar.time
        return format.format(Date(date)) == format.format(yesterday)
    }

    fun calculateTimeStampDifference(timeStamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val difference = currentTime - timeStamp
        return when {
            difference < 60000 -> "Just now"
            difference < 3600000 -> "${difference / 60000}m"
            difference < 86400000 -> "${difference / 3600000}h"
            else -> "${difference / 86400000}d"
        }
    }


}