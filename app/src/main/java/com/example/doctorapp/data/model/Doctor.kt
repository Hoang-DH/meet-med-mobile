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
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}
