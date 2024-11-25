package com.example.doctorapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class Doctor(
    var id: String? = null,
    var user: User? = null,
    val speciality: String? = null,
    val description: String? = null,
    val phone: String? = null,
    val rating: Float? = null,
    val numberOfPatients: Int = 0,
    val reviewCount: Int = 0,
    val imageUrl: String? = null ,
    val yoe: Int? = null,
    val isFavorite: Boolean = false,
    var department: Department? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(Department::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeString(speciality)
        parcel.writeString(description)
        parcel.writeString(phone)
        if (rating != null) {
            parcel.writeFloat(rating)
        }
        parcel.writeInt(numberOfPatients)
        parcel.writeInt(reviewCount)
        parcel.writeString(imageUrl)
        if (yoe != null) {
            parcel.writeInt(yoe)
        }
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
