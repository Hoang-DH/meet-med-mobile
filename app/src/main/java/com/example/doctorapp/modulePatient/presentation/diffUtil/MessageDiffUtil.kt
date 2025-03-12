package com.example.doctorapp.modulePatient.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.doctorapp.domain.model.Message
import com.example.doctorapp.domain.model.MessageData

class MessageDiffUtil: DiffUtil.ItemCallback<MessageData>() {
    override fun areItemsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
        return oldItem == newItem
    }
}