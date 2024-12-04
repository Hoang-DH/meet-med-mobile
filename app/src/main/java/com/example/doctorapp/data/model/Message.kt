package com.example.doctorapp.data.model

data class Message(
    var id: String? = null,
    var messageContent: String? = null,
    var senderId: String? = null,
    var sendDate: String? = null,
    var messageType: String? = null,
    var read: Int? = null,
)
