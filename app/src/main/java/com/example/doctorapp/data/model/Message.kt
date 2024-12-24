package com.example.doctorapp.data.model

import com.example.doctorapp.constant.MessageStatus
import com.google.gson.annotations.SerializedName

data class Message(

    @SerializedName("createdAt")
    var createdAt: String? = null,

    @SerializedName("updatedAt")
    var updatedAt: String? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("content")
    var messageContent: String? = null,

    @SerializedName("patient")
    var patient: Patient? = null,

    @SerializedName("doctor")
    var doctor: Doctor? = null,

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("status")
    var status: MessageStatus? = null,
)
