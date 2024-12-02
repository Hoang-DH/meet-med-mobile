package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

//shift of doctor for patient to register
data class DoctorBookingShift(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("shift")
    var shift: Shift? = null,
    @SerializedName("timeSlots")
    var timeSlot: ArrayList<TimeSlot>? = null,
)