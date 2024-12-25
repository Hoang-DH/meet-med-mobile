package com.example.doctorapp.data.model

import android.os.Parcel
import android.os.Parcelable
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

    @SerializedName("lastMessage")
    var lastSentMessageContent: Message? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("patient"),
        parcel.readParcelable(Doctor::class.java.classLoader),
        TODO("lastSentMessageContent")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
        parcel.writeString(id)
        parcel.writeParcelable(doctor, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageRoom> {
        override fun createFromParcel(parcel: Parcel): MessageRoom {
            return MessageRoom(parcel)
        }

        override fun newArray(size: Int): Array<MessageRoom?> {
            return arrayOfNulls(size)
        }
    }
}