package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

data class BookingShift (
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("symptoms")
    var symptoms: String? = null,
    @SerializedName("registeredShiftTimeSlot")
    var timeSlot: TimeSlot? = null,
    @SerializedName("doctor")
    var doctor: Doctor? = null,
    @SerializedName("patient")
    var patient: Patient? = null
)