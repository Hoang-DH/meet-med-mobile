package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class DoctorShift (
    @SerializedName("id")
    val id: Int,
    @SerializedName("startTime")
    val startTime: Instant,
    @SerializedName("endTime")
    val endTime: Instant,
    var isRegistered: Boolean = false
)