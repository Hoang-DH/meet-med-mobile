package com.example.doctorapp.modulePatient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.data.model.TimeSlot
import com.example.doctorapp.databinding.TimeItemBinding
import com.example.doctorapp.utils.DateUtils

class TimeSlotAdapter() : ListAdapter<TimeSlot, TimeSlotAdapter.ShiftViewHolder>(TimeSlotDiffUtil()) {
    private var selectedPos = 0
    private var onTimeSlotClick: OnTimeSlotClick? = null
    inner class ShiftViewHolder(private val binding: TimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timeSlot: TimeSlot, isFirstItem: Boolean = false) {
            binding.apply {
                tvTime.text = buildString {
                    append(timeSlot.startTime?.let { DateUtils.convertInstantToTime(it) })
                    append("-")
                    append(timeSlot.endTime?.let { DateUtils.convertInstantToTime(it) })
                }
                root.isSelected = selectedPos == adapterPosition
                if (selectedPos == adapterPosition) {
                    tvTime.setTextColor(root.context.resources.getColor(android.R.color.white, null))
                } else {
                    tvTime.setTextColor(root.context.resources.getColor(android.R.color.black, null))
                }
                root.setOnClickListener {
                    notifyItemChanged(selectedPos)
                    selectedPos = adapterPosition
                    notifyItemChanged(selectedPos)
                    onTimeSlotClick?.onTimeSlotClick(timeSlot)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {
        val binding = TimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.bind(getItem(position))
        if(position == 0){
            holder.bind(getItem(position), true)
        }
    }

    class TimeSlotDiffUtil() : DiffUtil.ItemCallback<TimeSlot>() {
        override fun areItemsTheSame(oldItem: TimeSlot, newItem: TimeSlot): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TimeSlot, newItem: TimeSlot): Boolean {
            return oldItem == newItem
        }

    }

    fun setOnTimeSlotClick(onTimeSlotClick: OnTimeSlotClick) {
        this.onTimeSlotClick = onTimeSlotClick
    }

    interface OnTimeSlotClick {
        fun onTimeSlotClick(timeSlot: TimeSlot)
    }

}

