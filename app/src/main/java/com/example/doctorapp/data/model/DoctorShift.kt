package com.example.doctorapp.data.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.time.Instant

private val gson = Gson()
data class DoctorShift (
    @SerializedName("id")
    val id: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    var isRegistered: Boolean = false
) {
    public fun toJson() = gson.toJson(this)
    companion object {
        public fun fromJson(json: String) = gson.fromJson(json, DoctorShift::class.java)
    }
}