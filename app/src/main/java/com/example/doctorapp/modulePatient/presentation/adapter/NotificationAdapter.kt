package com.example.doctorapp.modulePatient.presentation.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.constant.NotificationConstant
import com.example.doctorapp.data.model.Notification
import com.example.doctorapp.data.model.NotificationData
import com.example.doctorapp.data.model.NotificationTimeStamp
import com.example.doctorapp.databinding.ItemNotiContentBinding
import com.example.doctorapp.databinding.ItemNotiTimestampBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.modulePatient.presentation.diffUtil.NotificationDiffUtil
import com.example.doctorapp.utils.DateUtils
import kotlin.jvm.Throws

class NotificationAdapter(private val context: Context) : BaseAdapterLoadMore<Notification>(
    NotificationDiffUtil()
) {
//    var footers: MutableList<View> = ArrayList()
//    private val notiList: MutableList<Notification> = mutableListOf()
    companion object {
        private const val TYPE_TIMESTAMP = 0
        private const val TYPE_CONTENT = 1
//        private const val TYPE_FOOTER = 2
    }

    private var onNotificationClickListener: OnNotificationClickListener? = null

    fun setOnNotificationClickListener(listener: OnNotificationClickListener) {
        onNotificationClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is NotificationData -> TYPE_CONTENT
            is NotificationTimeStamp -> TYPE_TIMESTAMP
        }
    }

    override fun onBindViewHolderNormal(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimestampNotificationViewHolder -> {
                holder.bind(getItem(position) as NotificationTimeStamp)
            }
            is ContentNotificationViewHolder -> {
                holder.bind(getItem(position) as NotificationData)
            }
        }
    }

    override fun onCreateViewHolderNormal(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

//    override fun getItemCount(): Int {
//        return notiList.size + footers.size
//    }

    inner class TimestampNotificationViewHolder(private val binding: ItemNotiTimestampBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationTimeStamp) {
            binding.apply {
                tvTimestamp.text = notification.titleTimeStamp
            }
        }
    }

    inner class ContentNotificationViewHolder(private val binding: ItemNotiContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: NotificationData) {
            if(notification.type == NotificationConstant.Type.BOOK_APPOINTMENT_SUCCESS){
                notification.image = R.drawable.ic_noti_success
            }
            if(notification.type == NotificationConstant.Type.APPOINTMENT_REMINDER || notification.type == NotificationConstant.Type.WORKING_SHIFT_REMINDER){
                notification.image = R.drawable.ic_appointment
            }

            binding.apply {
                tvTitle.text = notification.title
                Glide.with(context)
                    .load(notification.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgNoti)
                tvTimestamp.text = notification.createdAt?.let { DateUtils.calculateTimeStampDifference(it) }
                tvContent.text = notification.content
                clNotiItem.isActivated = (notification.status == NotificationConstant.Status.OPEN)
                clNotiItem.setOnClickListener {
                    onNotificationClickListener?.onNotificationClick(notification)
                }
            }
        }
    }

    interface OnNotificationClickListener {
        fun onNotificationClick(notification: NotificationData)
    }

//    inner class FooterViewHolder internal constructor(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        var base: FrameLayout
//
//        init {
//            base = itemView as FrameLayout
//            val lp = FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            lp.gravity = Gravity.CENTER_HORIZONTAL
//            base.layoutParams = lp
//        }
//    }

//    private fun prepareHeaderFooter(vh: FooterViewHolder, view: View) {
//        //empty out our FrameLayout and replace with our header/footer
//        try {
//            vh.base.removeAllViews()
//            vh.base.addView(view)
//        } catch (e: Exception) {
//        }
//    }
//
//    fun addFooter(view: View) {
//        if(!footers.contains(view)){
//            footers.add(view)
//            notifyItemInserted(notiList.size + footers.size - 1)
//        }
//    }

}