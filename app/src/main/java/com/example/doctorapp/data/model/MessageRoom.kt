package com.example.doctorapp.data.model

data class MessageRoom(
    var id: String? = null,
    var title: String? = null,
    var content: String? = null,
    var status: String? = null,
    var lastSentTimeStamp: String? = null,
    var type: String? = null,
    var objectData: String? = null,
    var imageUrl: String? = null,
    var sender: User? = null
)