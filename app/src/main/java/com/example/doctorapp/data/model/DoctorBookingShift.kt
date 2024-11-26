package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

data class DoctorBookingShift(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("shift")
    var shift: Shift? = null,
    @SerializedName("timeSlots")
    var timeSlot: List<TimeSlot>? = null,
)