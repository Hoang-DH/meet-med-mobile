package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.data.model.Notification
import com.example.doctorapp.data.model.NotificationData
import com.example.doctorapp.data.model.NotificationTimeStamp
import com.example.doctorapp.databinding.ItemNotiContentBinding
import com.example.doctorapp.databinding.ItemNotiTimestampBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.NotificationDiffUtil
import com.example.doctorapp.utils.DateUtils

class NotificationAdapter(private val context: Context): ListAdapter<Notification, RecyclerView.ViewHolder>(
    NotificationDiffUtil()
) {

    companion object {
        private const val TYPE_TIMESTAMP = 0
        private const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is NotificationData -> TYPE_CONTENT
            is NotificationTimeStamp -> TYPE_TIMESTAMP
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TIMESTAMP -> {
                val binding = ItemNotiTimestampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TimestampNotificationViewHolder(binding)
            }
            TYPE_CONTENT -> {
                val binding = ItemNotiContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ContentNotificationViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is NotificationData -> (holder as ContentNotificationViewHolder).bind(getItem(position) as NotificationData)
            is NotificationTimeStamp -> (holder as TimestampNotificationViewHolder).bind(getItem(position) as NotificationTimeStamp)
        }
    }

    inner class TimestampNotificationViewHolder(private val binding: ItemNotiTimestampBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationTimeStamp) {
            binding.apply {
                tvTimestamp.text = notification.titleTimeStamp
            }
        }
    }

    inner class ContentNotificationViewHolder(private val binding: ItemNotiContentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationData) {
            binding.apply {
                tvTitle.text = notification.title
                Glide.with(context)
                    .load(notification.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgNoti)
                tvTimestamp.text = DateUtils.calculateTimeStampDifference(notification.timeStamp)
                tvContent.text = notification.content
                clNotiItem.isActivated = notification.isRead
            }
        }
    }


}