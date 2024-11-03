package com.example.doctorapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class Doctor(
    val id: Int,
    var name: String,
    val speciality: String,
    val description: String,
    val phone: String,
    val email: String,
    val rating: Float,
    val numberOfPatients: Int,
    val reviewCount: Int,
    val imageUrl: String,
    val yoe: Int,
    val isFavorite: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(speciality)
        parcel.writeString(description)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeFloat(rating)
        parcel.writeInt(numberOfPatients)
        parcel.writeInt(reviewCount)
        parcel.writeString(imageUrl)
        parcel.writeInt(yoe)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Doctor> {
        override fun createFromParcel(parcel: Parcel): Doctor {
            return Doctor(parcel)
        }

        override fun newArray(size: Int): Array<Doctor?> {
            return arrayOfNulls(size)
        }
    }


}
