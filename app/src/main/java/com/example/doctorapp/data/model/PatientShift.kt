package com.example.doctorapp.data.model

data class PatientShift(
    val id: Int,
    val startTime: Long,
    val endTime: Long,
    val slots: Int
)