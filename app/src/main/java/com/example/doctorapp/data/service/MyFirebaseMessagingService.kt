package com.example.doctorapp.data.service

import android.util.Log
import com.example.doctorapp.utils.NotificationUtils
import com.example.doctorapp.utils.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Prefs.getInstance(applicationContext).deviceToken = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("HoangDH", "From: ${message.data}/ ${message.notification?.body}")
        val notificationUtils = NotificationUtils(applicationContext)
        notificationUtils.showNotification(message.data)
    }
}