package com.example.doctorapp.presentation.utils

import com.example.doctorapp.data.model.NotificationData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

object DateUtils {

    private val format = SimpleDateFormat("EEEE, MMMM d, yyyy")

    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        return format.format(date)
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