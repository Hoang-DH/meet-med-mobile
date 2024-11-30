package com.example.doctorapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.DoctorHomeContainerFragment
import com.example.doctorapp.modulePatient.presentation.homeContainer.HomeContainerFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationUtils(private val mContext: Context) {
    fun showNotification(message: Map<String, String>) {
        // show notification
        val channelName = mContext.getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(mContext.getString(R.string.app_name), channelName, importance)
        channel.setSound(null, null)
        val notificationManager: NotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val notificationIntent = getNotificationIntent(message["type"]!!)
        val requestCode = createID()
        val pendingIntentFlag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val resultPendingIntent = PendingIntent.getActivity(
            mContext, requestCode, notificationIntent, pendingIntentFlag
        )
        val mBuilder = NotificationCompat.Builder(mContext, mContext.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_logo_noti)
            .setColor(mContext.resources.getColor(R.color.color_primary))
            .setContentTitle(message["title"])
            .setContentText(message["body"])
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)

        Glide.with(mContext).asBitmap().load(R.drawable.logo).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                mBuilder.setLargeIcon(resource)
                notificationManager.notify(requestCode, mBuilder.build())
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                TODO("Not yet implemented")
            }

        })
        notificationManager.notify(requestCode, mBuilder.build())
    }

    private fun getNotificationIntent(type: String): Intent {
        var intent = Intent()
        when (type) {
            Define.NotificationType.BOOK_APPOINTMENT_SUCCESS -> {
                intent = Intent(mContext, HomeContainerFragment::class.java)
                intent.putExtra(Define.IntentKey.NOTIFICATION_TYPE, Define.NotificationType.BOOK_APPOINTMENT_SUCCESS)
            }

            Define.NotificationType.CANCEL_APPOINTMENT_SUCCESS -> {
                intent = Intent(mContext, HomeContainerFragment::class.java)
                intent.putExtra(Define.IntentKey.NOTIFICATION_TYPE, Define.NotificationType.CANCEL_APPOINTMENT_SUCCESS)
            }

            Define.NotificationType.APPOINTMENT_REMINDER -> {
                intent = Intent(mContext, HomeContainerFragment::class.java)
                intent.putExtra(Define.IntentKey.NOTIFICATION_TYPE, Define.NotificationType.APPOINTMENT_REMINDER)
            }

            Define.NotificationType.WORKING_SHIFT_REMINDER -> {
                intent = Intent(mContext, DoctorHomeContainerFragment::class.java)
                intent.putExtra(Define.IntentKey.NOTIFICATION_TYPE, Define.NotificationType.WORKING_SHIFT_REMINDER)
            }
        }
            return intent
        }

        private fun createID(): Int {
            val now = Date()
            return SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt()
        }
    }