package com.example.doctorapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.databinding.DoctorSmallItemBinding
import com.example.doctorapp.presentation.diffUtil.DoctorDiffUtil

class SmallDoctorItemAdapter(private val context: Context, private val onDoctorClickListener: ((Doctor) -> Unit)? = null): ListAdapter<Doctor, SmallDoctorItemAdapter.SmallDoctorItemViewHolder>(DoctorDiffUtil()) {

    inner class SmallDoctorItemViewHolder(private val binding: DoctorSmallItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(doctor: Doctor){
            binding.apply {
                Glide.with(context)
                    .load(doctor.imageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivDoctor)
                tvDoctorName.text = doctor.name
                root.setOnClickListener {
                    onDoctorClickListener?.invoke(doctor)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallDoctorItemViewHolder {
        val binding = DoctorSmallItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmallDoctorItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmallDoctorItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}