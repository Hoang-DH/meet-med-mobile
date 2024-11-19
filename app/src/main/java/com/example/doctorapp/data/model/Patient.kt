package com.example.doctorapp.data.model

import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.google.gson.annotations.SerializedName

data class Patient(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("user")
    val user: User = User(),

    @SerializedName("addressLine")
    val addressLine: String = "",

    @SerializedName("district")
    val district: String = "",

    @SerializedName("city")
    val city: String = "",

    @SerializedName("insuranceCode")
    val insuranceCode: String = "",
)
