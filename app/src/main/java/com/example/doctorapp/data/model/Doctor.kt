package com.example.doctorapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class Doctor(
    val id: Int,
    val name: String,
    val speciality: String,
    val description: String,
    val phone: String,
    val email: String,
    val rating: Float,
    val numberOfPatients: Int,
    val reviewCount: Int,
    val imageUrl: String,
    val yoe: Int,
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

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
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
