package com.example.doctorapp.constant

import com.google.gson.annotations.SerializedName

enum class MessageStatus (value: Int) {
    SENDING(0),
    @SerializedName("SENT")
    SENT(1),

    @SerializedName("READ")
    SEEN(3),
    FAILED(4)
}