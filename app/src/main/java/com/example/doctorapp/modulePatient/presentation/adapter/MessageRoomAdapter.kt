package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.databinding.MessageListItemBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.MessageRoomDiffUtil

class MessageRoomAdapter(private val context: Context): ListAdapter<MessageRoom, MessageRoomAdapter.MessageRoomAdapterViewHolder> (MessageRoomDiffUtil()) {

    inner class MessageRoomAdapterViewHolder(private val binding: MessageListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageRoom: MessageRoom) {
            binding.apply {
                tvMessageContent.text = messageRoom.lastSentMessageContent
                tvTimestamp.text = messageRoom.lastSentTimeStamp
                tvUsername.text = messageRoom.sender?.fullName
                if(messageRoom.status == "read"){
                    tvMessageContent.setTypeface(null, android.graphics.Typeface.NORMAL)
                    tvTimestamp.setTypeface(null, android.graphics.Typeface.NORMAL)
                    ivUnreadMessage.visibility = android.view.View.GONE
                } else {
                    tvMessageContent.setTypeface(null, android.graphics.Typeface.BOLD)
                    tvTimestamp.setTypeface(null, android.graphics.Typeface.BOLD)
                    ivUnreadMessage.visibility = android.view.View.VISIBLE
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageRoomAdapterViewHolder {
        val binding = MessageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageRoomAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageRoomAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}