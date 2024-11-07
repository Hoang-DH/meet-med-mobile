package com.example.doctorapp.data.model

import java.time.Instant

data class DoctorShift (
    val id: Int,
    val startTime: Instant,
    val endTime: Instant,
    var isRegistered: Boolean
)