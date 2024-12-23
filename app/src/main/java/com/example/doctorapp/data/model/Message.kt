package com.example.doctorapp.data.model

import com.example.doctorapp.constant.MessageStatus

data class Message(
    var id: String? = null,
    var messageContent: String? = null,
    var senderId: String? = null,
    var sendDate: String? = null,
    var messageType: String? = null,
    var status: MessageStatus? = null,
    var attachmentUrl: String? = null,
    var senderProfile: User? = null
)
