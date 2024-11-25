package com.example.doctorapp.data.dto

import com.google.gson.annotations.SerializedName

data class ShiftDTO(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("startTime")
    val startTime: String? = null,
    @SerializedName("endTime")
    val endTime: String?  = null,
)