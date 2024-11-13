package com.example.doctorapp.data.dto

import com.example.doctorapp.data.model.DoctorShift
import com.google.gson.annotations.SerializedName

data class DoctorShiftToRegister(
    @SerializedName("data")
    val data: List<DoctorShift>
)