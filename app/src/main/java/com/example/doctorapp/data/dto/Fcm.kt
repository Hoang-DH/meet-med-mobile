package com.example.doctorapp.data.dto

import com.google.gson.annotations.SerializedName

data class Fcm(
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("userId")
    var userId: String? = null
)