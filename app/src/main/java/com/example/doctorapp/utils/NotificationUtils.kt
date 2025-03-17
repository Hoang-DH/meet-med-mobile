package com.example.doctorapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.constant.NotificationConstant
import com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.DoctorHomeContainerFragment
import com.example.doctorapp.modulePatient.presentation.container.MainActivity
import com.example.doctorapp.modulePatient.presentation.homeContainer.HomeContainerFragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationUtils(private val mContext: Context) {
    fun showNotification(message: Map<String, String>) {
        // show notification
        val channelName = mContext.getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(mContext.getString(R.string.app_name), channelName, importance)
        channel.setSound(null, null)
        val notificationManager: NotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val notificationIntent = getNotificationIntent(message["type"]!!, message["chatBoxId"]?.substring(1, message["chatBoxId"]!!.length - 1))
        val requestCode = createID()
        val pendingIntentFlag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val resultPendingIntent = PendingIntent.getActivity(
            mContext, requestCode, notificationIntent, pendingIntentFlag
        )
        var content = if(message["content"]!!.endsWith(".mp4")  || message["content"]!!.endsWith(".jpg")) {
            "You have a new media message"
        } else {
            message["content"]!!
        }
        val mBuilder = NotificationCompat.Builder(mContext, mContext.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_logo_noti)
            .setColor(mContext.resources.getColor(R.color.color_primary))
            .setContentTitle(message["title"])
            .setContentText(content)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

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

    private fun getNotificationIntent(type: String, chatBoxId: String? = null): Intent {
        val intent = Intent(mContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        if(chatBoxId != null) {
            val bundle = Bundle()
            bundle.putString(Define.BundleKey.CHAT_BOX_ID, chatBoxId)
            intent.putExtras(bundle)
            return intent
        }
        when (type) {
            NotificationConstant.Type.BOOK_APPOINTMENT_SUCCESS -> {
                intent.action =  NotificationConstant.Type.BOOK_APPOINTMENT_SUCCESS
            }

            NotificationConstant.Type.CANCEL_APPOINTMENT_SUCCESS -> {
                intent.action = NotificationConstant.Type.CANCEL_APPOINTMENT_SUCCESS
            }

            NotificationConstant.Type.APPOINTMENT_REMINDER -> {
                intent.action = NotificationConstant.Type.APPOINTMENT_REMINDER
            }

            NotificationConstant.Type.WORKING_SHIFT_REMINDER -> {
                intent.action = NotificationConstant.Type.WORKING_SHIFT_REMINDER
            }
        }
            return intent
        }

        private fun createID(): Int {
            val now = Date()
            return SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt()
        }
    }