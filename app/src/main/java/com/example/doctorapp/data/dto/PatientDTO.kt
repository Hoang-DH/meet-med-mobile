package com.example.doctorapp.data.dto
import com.google.gson.annotations.SerializedName

data class PatientDTO(

    @SerializedName("addressLine")
    val addressLine: String? = null,

    @SerializedName("district")
    val district: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("insuranceCode")
    val insuranceCode: String? = null,

    @SerializedName("user")
    var user: UserDTO? = null,
)
