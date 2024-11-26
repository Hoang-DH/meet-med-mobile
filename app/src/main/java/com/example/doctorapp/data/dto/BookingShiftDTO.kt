package com.example.doctorapp.data.dto

import com.example.doctorapp.data.model.TimeSlot
import com.google.gson.annotations.SerializedName

data class BookingShiftDTO (
    @SerializedName("symptoms")
    var symptoms: String? = null,
    @SerializedName("registeredShiftTimeSlot")
    var timeSlot: TimeSlot? = null,
)