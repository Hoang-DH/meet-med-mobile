package com.example.doctorapp.data.model

sealed class Notification()
data class NotificationData(
    val title: String,
    val content: String,
    val image: Int,
    val isRead: Boolean,
    val timeStamp: Long,
) : Notification()

data class NotificationTimeStamp(
    val titleTimeStamp: String,
) : Notification()

