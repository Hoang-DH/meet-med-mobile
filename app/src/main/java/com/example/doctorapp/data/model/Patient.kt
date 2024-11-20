package com.example.doctorapp.data.model

import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.google.gson.annotations.SerializedName

data class Patient(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("user")
    var user: User? = null,

    @SerializedName("addressLine")
    val addressLine: String? = null,

    @SerializedName("district")
    val district: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("insuranceCode")
    val insuranceCode: String? = null,
)
