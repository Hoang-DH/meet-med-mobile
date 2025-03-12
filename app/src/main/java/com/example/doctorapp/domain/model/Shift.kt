package com.example.doctorapp.domain.model
import com.google.gson.annotations.SerializedName

data class Shift(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("startTime")
    val startTime: String? = null,
    @SerializedName("endTime")
    val endTime: String? = null,
)