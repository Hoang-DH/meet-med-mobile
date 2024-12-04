package com.example.doctorapp.data.model

data class MessageRoom(
    var id: String? = null,
    var status: String? = null,
    var lastSentMessageContent: String? = null,
    var lastSentTimeStamp: String? = null,
    var lastSentMessageType: String? = null,
    var type: String? = null,
    var sender: User? = null
)