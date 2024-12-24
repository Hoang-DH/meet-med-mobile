package com.example.doctorapp.data.model

import com.google.gson.annotations.SerializedName

data class MessageRoom(
    @SerializedName("createdAt")
    var createdAt: String? = null,

    @SerializedName("updatedAt")
    var updatedAt: String? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("patient")
    var patient: Patient? = null,

    @SerializedName("doctor")
    var doctor: Doctor? = null,

    var status: String? = null,
    var lastSentMessageContent: String? = null,
    var lastSentTimeStamp: String? = null,
    var lastSentMessageType: String? = null,
    var type: String? = null,
    var sender: User? = null
)