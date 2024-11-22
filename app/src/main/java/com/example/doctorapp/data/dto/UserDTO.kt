package com.example.doctorapp.data.dto

import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("fullName")
    val fullName: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("gender")
    val gender: String?= null,
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("dob")
    val dob: String? = null
)