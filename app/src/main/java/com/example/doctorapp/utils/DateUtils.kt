package com.example.doctorapp.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

object DateUtils {

    private val format = SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).apply {
        timeZone = Calendar.getInstance().timeZone
    }

    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        return format.format(date)
    }

    fun convertDateToLong(date: String): Long {
        return format.parse(date)?.time ?: 0
    }


    fun convertInstantToTime(time: String): String {
        val instant = Instant.parse(time)
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatterTime.format(dateTime)
    }

    fun convertInstantToDate(time: String?, pattern: String): String {
        val instant = Instant.parse(time)
        val formatterDate = DateTimeFormatter.ofPattern(pattern)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatterDate.format(dateTime)
    }

    fun convertInstantToDayOfWeek(time: String): String {
        val instant = Instant.parse(time)
        val formatterDayOfWeek = DateTimeFormatter.ofPattern("EEEE")
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatterDayOfWeek.format(dateTime)
    }

    fun checkDateIsSameDay(instantString1: String?, instantString2: String?): Boolean {
        val instant1 = Instant.parse(instantString1)
        val instant2 = Instant.parse(instantString2)
        val dateTime1 = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault())
        val dateTime2 = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault())
        return dateTime1.toLocalDate() == dateTime2.toLocalDate()
    }

    fun isToday(instantString: String?): Boolean {
        val instant = Instant.parse(instantString)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val today = LocalDateTime.now(ZoneId.systemDefault())
        return dateTime.toLocalDate() == today.toLocalDate()
    }

    fun isYesterday(instantString: String?): Boolean {
        val instant = Instant.parse(instantString)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val yesterday = LocalDateTime.now(ZoneId.systemDefault()).minusDays(1)
        return dateTime.toLocalDate() == yesterday.toLocalDate()
    }

    fun calculateTimeStampDifference(timeStamp: String): String {
        val currentTime = System.currentTimeMillis()
        val parsedTimeStamp = Instant.parse(timeStamp).toEpochMilli()
        val difference = currentTime - parsedTimeStamp
        return when {
            difference < 60000 -> "Just now"
            difference < 3600000 -> "${difference / 60000}m"
            difference < 86400000 -> "${difference / 3600000}h"
            else -> "${difference / 86400000}d"
        }
    }


}