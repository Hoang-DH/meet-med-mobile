package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

data class BookingShift (
    @SerializedName("symptoms")
    var symptoms: String? = null,
    @SerializedName("registeredShiftTimeSlot")
    var timeSlot: TimeSlot? = null,
)