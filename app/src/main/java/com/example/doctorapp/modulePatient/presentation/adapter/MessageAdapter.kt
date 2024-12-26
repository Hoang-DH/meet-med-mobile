package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.constant.MessageStatus
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.data.model.MessageData
import com.example.doctorapp.data.model.MessageTimeStamp
import com.example.doctorapp.databinding.ItemMessageReceivedBinding
import com.example.doctorapp.databinding.ItemMessageSentBinding
import com.example.doctorapp.databinding.ItemMessageTimestampBinding
import com.example.doctorapp.databinding.ItemNotiTimestampBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.domain.core.base.BaseReverseAdapterLoadMore
import com.example.doctorapp.modulePatient.presentation.diffUtil.MessageDiffUtil

class MessageAdapter(private val context: Context) : BaseReverseAdapterLoadMore<MessageData>(MessageDiffUtil()) {

    companion object {
        private const val TYPE_MESSAGE_SENT = 0
        private const val TYPE_MESSAGE_RECEIVED = 1
        private const val TYPE_TIME_STAMP = 2
    }

    private var rotation: Animation? = null
    private var onMediaItemClickListener: OnMediaItemClickListener? = null

    interface OnMediaItemClickListener {
        fun onMediaItemClick(message: Message)
    }

    fun setOnMediaItemClickListener(listener: OnMediaItemClickListener) {
        onMediaItemClickListener = listener
    }

    inner class ItemMessageSentViewHolder(private val binding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                tvMessageContent.visibility = View.GONE
                tvFile.visibility = View.GONE
                cvImage.visibility = View.GONE
                ivPlayVideo.visibility = View.GONE
                when (message.type) {
                    "TEXT" -> {
                        tvMessageContent.text = message.messageContent
                        tvMessageContent.visibility = View.VISIBLE
                        tvFile.visibility = View.GONE
                        cvImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                    "IMAGE" -> {
                        tvMessageContent.visibility = View.GONE
                        cvImage.visibility = View.VISIBLE
                        tvFile.visibility = View.GONE
                        cvImage.visibility = View.VISIBLE
                        ivPlayVideo.visibility = View.GONE
                        Glide.with(context)
                            .load(message.messageContent)
                            .apply(RequestOptions().transform(CenterCrop()))
                            .into(ivImage)
                        cvImage.setOnClickListener {
                            onMediaItemClickListener?.onMediaItemClick(message)
                        }
                    }
                    "VIDEO" -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.GONE
                        cvImage.visibility = View.VISIBLE
                        ivPlayVideo.visibility = View.VISIBLE
                    }
                    else -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.VISIBLE
                        cvImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                }
                when(message.status){
                    MessageStatus.SENT -> {
                        tvMessageStatus.text = context.getString(R.string.sent)
                        stopAnimationLoading()
                        ivLoading.visibility = View.GONE
                    }
                    MessageStatus.SENDING -> {
                        tvMessageStatus.text = context.getString(R.string.sending)
                        startAnimationLoading()
                        ivLoading.visibility = View.VISIBLE
                        ivLoading.startAnimation(rotation)
                    }
                    MessageStatus.SEEN -> {
                        tvMessageStatus.text = context.getString(R.string.seen)
                        stopAnimationLoading()
                        ivLoading.visibility = View.GONE
                    }
                    MessageStatus.FAILED -> {
                        tvMessageStatus.text = context.getString(R.string.failed)
                        tvMessageStatus.setTextColor(context.getColor(R.color.red))
                        stopAnimationLoading()
                        ivLoading.visibility = View.GONE
                    }

                    null -> TODO()
                }

            }
        }
    }

    inner class ItemMessageReceived(private val binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                ivAvatar.setImageResource(R.drawable.img)
                tvMessageContent.visibility = View.GONE
                tvFile.visibility = View.GONE
                cvImage.visibility = View.GONE
                ivPlayVideo.visibility = View.GONE
                when (message.type) {
                    "TEXT" -> {
                        tvMessageContent.text = message.messageContent
                        tvMessageContent.visibility = View.VISIBLE
                        tvFile.visibility = View.GONE
                        cvImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                    "IMAGE" -> {
                        tvMessageContent.visibility = View.GONE
                        cvImage.visibility = View.VISIBLE
                        tvFile.visibility = View.GONE
                        cvImage.visibility = View.VISIBLE
                        ivPlayVideo.visibility = View.GONE
                        Glide.with(context)
                            .load(message.messageContent)
                            .apply(RequestOptions().transform(CenterCrop()))
                            .into(ivImage)
                        cvImage.setOnClickListener {
                            onMediaItemClickListener?.onMediaItemClick(message)
                        }
                    }
                    "VIDEO" -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.GONE
                        cvImage.visibility = View.VISIBLE
                        ivPlayVideo.visibility = View.VISIBLE
                    }
                    else -> {
                        tvMessageContent.visibility = View.GONE
                        tvFile.visibility = View.VISIBLE
                        cvImage.visibility = View.GONE
                        ivPlayVideo.visibility = View.GONE
                    }
                }

            }
        }
    }

    inner class ItemTimeStampViewHolder(private val binding: ItemMessageTimestampBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MessageTimeStamp) {
            binding.apply {
                tvMessageTimestamp.text = message.titleTimeStamp
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Message -> {
                if ((getItem(position) as Message).doctor != null) {
                    TYPE_MESSAGE_RECEIVED
                } else {
                    TYPE_MESSAGE_SENT
                }
            }
            else -> TYPE_TIME_STAMP
        }
    }

    override fun onBindViewHolderNormal(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemMessageSentViewHolder -> {
                holder.bind(getItem(position) as Message)
            }
            is ItemMessageReceived -> {
                holder.bind(getItem(position) as Message)
            }
            is ItemTimeStampViewHolder -> {
                holder.bind(getItem(position) as MessageTimeStamp)
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
                val binding = ItemMessageTimestampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemTimeStampViewHolder(binding)
            }
        }
    }

    private fun startAnimationLoading(){
        rotation = AnimationUtils.loadAnimation(context, R.anim.rotate_anim)

    }

    private fun stopAnimationLoading(){
        rotation?.cancel()
        rotation?.reset()
    }

}