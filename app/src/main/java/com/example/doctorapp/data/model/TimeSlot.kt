package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

data class TimeSlot(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("startTime")
    var startTime: String? = null,
    @SerializedName("endTime")
    var endTime: String? = null,
    @SerializedName("isAvailable")
    var isAvailable: Boolean = false
) {
    override fun toString(): String {
        return "TimeSlot(id=$id, startTime=$startTime, endTime=$endTime, isAvailable=$isAvailable)"
    }
}