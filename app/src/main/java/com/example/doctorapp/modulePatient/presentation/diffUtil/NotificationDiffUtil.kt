package com.example.doctorapp.modulePatient.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.domain.model.Notification

class NotificationDiffUtil: DiffUtil.ItemCallback<Notification>() {
    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }
}