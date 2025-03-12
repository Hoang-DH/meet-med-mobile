package com.example.doctorapp.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.time.Instant

private val gson = Gson()


sealed class DoctorShifts
data class DoctorShift (
    @SerializedName("id")
    val id: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    var isRegistered: Boolean = false
): DoctorShifts() {
    public fun toJson() = gson.toJson(this)
    companion object {
        public fun fromJson(json: String) = gson.fromJson(json, DoctorShift::class.java)
    }
}
data class DoctorShiftDayOfWeek(
    val dayOfWeek: String,
): DoctorShifts()