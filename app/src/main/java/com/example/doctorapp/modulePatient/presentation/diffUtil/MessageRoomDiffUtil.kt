package com.example.doctorapp.modulePatient.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.data.model.MessageRoom

class MessageRoomDiffUtil: DiffUtil.ItemCallback<MessageRoom>() {
    override fun areItemsTheSame(oldItem: MessageRoom, newItem: MessageRoom): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageRoom, newItem: MessageRoom): Boolean {
        return oldItem == newItem
    }
}