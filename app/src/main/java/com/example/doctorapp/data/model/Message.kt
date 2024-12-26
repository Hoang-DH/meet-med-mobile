package com.example.doctorapp.data.model

import com.example.doctorapp.constant.MessageStatus
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

sealed class MessageData()

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
): MessageData()

data class MessageTimeStamp(
    var titleTimeStamp: String? = null,
): MessageData()

data class MessageWrapper(
    @SerializedName("message") val message: Message
)

fun convertJsonToMessage(jsonString: String): Message {
    val gson = Gson()
    val messageWrapper = gson.fromJson(jsonString, MessageWrapper::class.java)
    return messageWrapper.message
}
