package com.example.doctorapp.moduleDoctor.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.constant.MessageStatus
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.databinding.MessageListItemBinding
import com.example.doctorapp.modulePatient.presentation.diffUtil.MessageRoomDiffUtil
import com.example.doctorapp.utils.DateUtils

class DoctorMessageRoomAdapter(private val context: Context) :
    ListAdapter<MessageRoom, DoctorMessageRoomAdapter.DoctorMessageRoomAdapterViewHolder>(MessageRoomDiffUtil()) {

    private var onMessageRoomClickListener: OnMessageRoomClickListener? = null

    inner class DoctorMessageRoomAdapterViewHolder(private val binding: MessageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(messageRoom: MessageRoom) {
            binding.apply {
                tvMessageContent.text =
                    if (messageRoom.lastSentMessageContent?.doctor != null) "You: " + messageRoom.lastSentMessageContent?.messageContent else messageRoom.lastSentMessageContent?.messageContent
                tvTimestamp.text = messageRoom.lastSentMessageContent?.updatedAt?.let {
                    DateUtils.convertInstantToTime(
                        it
                    )
                }
                tvUsername.text = messageRoom.doctor?.user?.fullName
                if (messageRoom.lastSentMessageContent?.status == MessageStatus.SEEN || messageRoom.lastSentMessageContent?.doctor != null) {
                    tvMessageContent.setTypeface(null, android.graphics.Typeface.NORMAL)
                    tvTimestamp.setTypeface(null, android.graphics.Typeface.NORMAL)
                    ivUnreadMessage.visibility = android.view.View.GONE
                } else if (messageRoom.lastSentMessageContent == null) {
                    tvMessageContent.text = context.getString(R.string.start_conversation)
                    tvMessageContent.setTypeface(null, android.graphics.Typeface.NORMAL)
                    tvTimestamp.setTypeface(null, android.graphics.Typeface.NORMAL)
                    ivUnreadMessage.visibility = android.view.View.GONE
                } else {
                    tvMessageContent.setTypeface(null, android.graphics.Typeface.BOLD)
                    tvTimestamp.setTypeface(null, android.graphics.Typeface.BOLD)
                    ivUnreadMessage.visibility = android.view.View.VISIBLE
                }
                itemView.setOnClickListener {
                    onMessageRoomClickListener?.onMessageRoomClick(messageRoom)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorMessageRoomAdapterViewHolder {
        val binding = MessageListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorMessageRoomAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorMessageRoomAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnMessageRoomClickListener(onMessageRoomClickListener: OnMessageRoomClickListener) {
        this.onMessageRoomClickListener = onMessageRoomClickListener
    }


    interface OnMessageRoomClickListener {
        fun onMessageRoomClick(messageRoom: MessageRoom)
    }

}