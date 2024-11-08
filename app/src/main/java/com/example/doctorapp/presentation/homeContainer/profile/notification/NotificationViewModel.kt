package com.example.doctorapp.presentation.homeContainer.profile.notification

import android.util.Log
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Notification
import com.example.doctorapp.data.model.NotificationData
import com.example.doctorapp.data.model.NotificationTimeStamp
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.utils.DateUtils

class NotificationViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel

    fun getListNotification(): List<Notification> {
        val receivedNotificationList = listOf(
            NotificationData(
                title = "Appointment Success",
                content = "Your appointment with Dr. Garcia was successful.",
                image = R.drawable.ic_noti_success,
                isRead = true,
                timeStamp = 1730210933000 // Oct 29, 2024, 10:00 AM
            ),
            NotificationData(
                title = "Appointment Success",
                content = "Your appointment with Dr. Garcia was successful.",
                image = R.drawable.ic_noti_success,
                isRead = true,
                timeStamp = 1730210933000 // Oct 29, 2024, 10:00 AM
            ),
            NotificationData(
                title = "Appointment Success",
                content = "Your appointment with Dr. Garcia was successful.",
                image = R.drawable.ic_noti_success,
                isRead = true,
                timeStamp = 1730210933000 // Oct 29, 2024, 10:00 AM
            ),
            //create item for yesterday
            NotificationData(
                title = "Appointment Cancelled",
                content = "Your appointment with Dr. Clark has been cancelled.",
                image = R.drawable.ic_noti_cancelled,
                isRead = false,
                timeStamp = 1730300933000 // Oct 30, 2024, 3:00 PM
            ),
            NotificationData(
                title = "Scheduled Changed",
                content = "Your appointment with Dr. Martinez is now at 3 PM.",
                image = R.drawable.ic_noti_schedule_changed,
                isRead = false,
                timeStamp = 1730357933000 // Oct 31, 2024, 3:00 PM
            ),
            NotificationData(
                title = "Scheduled Changed",
                content = "Your appointment with Dr. Martinez is now at 3 PM.",
                image = R.drawable.ic_noti_schedule_changed,
                isRead = false,
                timeStamp = 1730357933000 // Oct 31, 2024, 3:00 PM
            ),
        )
        val updatedNotificationList = receivedNotificationList.map { notification ->
            if (DateUtils.convertLongToDate(notification.timeStamp).contains("2023")) {
                notification.copy(timeStamp = 1730210933000) // Update to a timestamp in 2024
            } else {
                notification
            }
        }

        return filterNotiTimestamp(ArrayList(updatedNotificationList))
    }

    private fun filterNotiTimestamp(notiDataList: ArrayList<NotificationData>): List<Notification> {
        val notificationList = ArrayList<Notification>(notiDataList.sortedByDescending { it.timeStamp })
        val sortedNotiDataList = notiDataList.sortedByDescending { it.timeStamp }
        var timeStampTitle: String

        for (i in sortedNotiDataList.size - 1 downTo 0) {
            val current = sortedNotiDataList[i]
            val previous = if (i > 0) sortedNotiDataList[i - 1] else null

            Log.e("HoangDH", "timeStamp: ${DateUtils.convertLongToDate(current.timeStamp)}")

            when {
                DateUtils.isToday(current.timeStamp) -> {
                    if (previous == null || !DateUtils.isToday(previous.timeStamp)) {
                        notificationList.add(i, NotificationTimeStamp("Today"))
                    }
                }

                DateUtils.isYesterday(current.timeStamp) -> {
                    if (previous == null || !DateUtils.isYesterday(previous.timeStamp)) {
                        notificationList.add(i, NotificationTimeStamp("Yesterday"))
                    }
                }

                else -> {
                    timeStampTitle = DateUtils.convertLongToDate(current.timeStamp)
                    if (previous == null || !DateUtils.checkDateIsSameDay(current.timeStamp, previous.timeStamp)) {
                        notificationList.add(i, NotificationTimeStamp(timeStampTitle))
                    }
                }
            }

        }
        return notificationList
    }

}