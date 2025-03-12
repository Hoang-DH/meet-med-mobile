package com.example.doctorapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

sealed class Notification()
data class NotificationData(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("objectData")
    var objectData: String? = null,
    var image: Int? = null
) : Notification()

data class NotificationTimeStamp(
    var titleTimeStamp: String? = null,
) : Notification()

