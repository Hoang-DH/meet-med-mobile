package com.example.doctorapp.moduleDoctor.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.domain.model.DoctorShift
import com.example.doctorapp.domain.model.DoctorShiftDayOfWeek
import com.example.doctorapp.domain.model.DoctorShifts
import com.example.doctorapp.databinding.ItemDayOfWeekBinding
import com.example.doctorapp.databinding.ShiftItemBinding
import com.example.doctorapp.moduleDoctor.presentation.diffUtil.DoctorShiftDiffUtil
import com.example.doctorapp.utils.DateUtils

class DoctorShiftAdapter(
    private val context: Context,
    private val isMyShift: Boolean = false
) : ListAdapter<DoctorShifts, RecyclerView.ViewHolder>(DoctorShiftDiffUtil()) {

    private var onShiftClickListener: OnShiftClickListener? = null

    companion object {
        private const val TYPE_DAY_OF_WEEK = 0
        private const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DoctorShift -> TYPE_CONTENT
            else -> TYPE_DAY_OF_WEEK
        }
    }

    inner class RegisterShiftViewHolder(private val binding: ShiftItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shift: DoctorShift) {
            binding.apply {
                if (isMyShift) {
                    cbShift.visibility = View.GONE
                }
                tvShift.text = String.format(
                    context.getString(R.string.string_date_time_of_shift),
                    DateUtils.convertInstantToTime(shift.startTime),
                    DateUtils.convertInstantToTime(shift.endTime),
                    DateUtils.convertInstantToDate(shift.startTime, "MMMM d, yyyy")
                )
                cbShift.isChecked = shift.isRegistered
                cbShift.setOnClickListener {
                    onShiftClickListener?.onShiftClick(shift)
                }
            }
        }
    }

    inner class DayOfWeekViewHolder(private val binding: ItemDayOfWeekBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dayOfWeek: DoctorShiftDayOfWeek) {
            binding.apply {
                tvDayOfWeek.text = dayOfWeek.dayOfWeek
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_DAY_OF_WEEK -> {
                val binding = ItemDayOfWeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DayOfWeekViewHolder(binding)
            }
            TYPE_CONTENT -> {
                val binding = ShiftItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RegisterShiftViewHolder(binding)
            }

            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItem(position)) {
            is DoctorShift -> (holder as RegisterShiftViewHolder).bind(getItem(position) as DoctorShift)
            is DoctorShiftDayOfWeek -> (holder as DayOfWeekViewHolder).bind((getItem(position) as DoctorShiftDayOfWeek))
        }
    }

    fun setOnShiftClickListener(onShiftClickListener: OnShiftClickListener) {
        this.onShiftClickListener = onShiftClickListener
    }

    interface OnShiftClickListener {
        fun onShiftClick(shift: DoctorShift)
    }
}