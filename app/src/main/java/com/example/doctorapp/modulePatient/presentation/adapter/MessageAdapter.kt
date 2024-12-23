package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.databinding.ItemMessageReceivedBinding
import com.example.doctorapp.databinding.ItemMessageSentBinding
import com.example.doctorapp.databinding.ItemNotiTimestampBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.modulePatient.presentation.diffUtil.MessageDiffUtil

class MessageAdapter(private val context: Context) : BaseAdapterLoadMore<Message>(MessageDiffUtil()) {

    companion object {
        private const val TYPE_MESSAGE_SENT = 0
        private const val TYPE_MESSAGE_RECEIVED = 1
        private const val TYPE_TIME_STAMP = 2
    }

    inner class ItemMessageSentViewHolder(private val binding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                when (message.messageType) {
                    "TEXT" -> {
                        tvMessageContent.text = message.messageContent
                        tvFile.visibility = View.GONE
                        ivImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                    "IMAGE" -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.GONE
                        ivImage.visibility = View.VISIBLE
                        ivPlayVideo.visibility = View.GONE
                        Glide.with(context)
                            .load(R.drawable.headache)
                            .into(ivImage)
                    }
                    "VIDEO" -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.VISIBLE
                        ivImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.VISIBLE
                    }
                    else -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.VISIBLE
                        ivImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                }

            }
        }
    }

    inner class ItemMessageReceived(private val binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                ivAvatar.setImageResource(R.drawable.img)
                when (message.messageType) {
                    "TEXT" -> {
                        tvMessageContent.text = message.messageContent
                        tvFile.visibility = View.GONE
                        ivImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                    "IMAGE" -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.GONE
                        ivImage.visibility = View.VISIBLE
                        ivPlayVideo.visibility = View.GONE
                        Glide.with(context)
                            .load(R.drawable.headache)
                            .into(ivImage)
                    }
                    "VIDEO" -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.VISIBLE
                        ivImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.VISIBLE
                    }
                    else -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.VISIBLE
                        ivImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                }

            }
        }
    }

    inner class ItemTimeStampViewHolder(private val binding: ItemNotiTimestampBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                tvTimestamp.text = message.sendDate
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).senderId){
            "1" -> TYPE_MESSAGE_SENT
            "2" -> TYPE_MESSAGE_RECEIVED
            else -> TYPE_TIME_STAMP
        }
    }

    override fun onBindViewHolderNormal(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemMessageSentViewHolder -> {
                holder.bind(getItem(position))
            }
            is ItemMessageReceived -> {
                holder.bind(getItem(position))
            }
            is ItemTimeStampViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun onCreateViewHolderNormal(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_MESSAGE_SENT -> {
                val binding = ItemMessageSentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemMessageSentViewHolder(binding)
            }

            TYPE_MESSAGE_RECEIVED -> {
                val binding = ItemMessageReceivedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemMessageReceived(binding)
            }

            else -> {
                val binding = ItemNotiTimestampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemTimeStampViewHolder(binding)
            }
        }
    }

}