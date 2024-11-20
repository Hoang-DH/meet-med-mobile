package com.example.doctorapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("fullName")
    val fullName: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("gender")
    val gender: Gender = Gender.MALE,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("age")
    val age: Int? = null,
    @SerializedName("phone")
    val phone: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        Gender.valueOf(parcel.readString().toString()),
        parcel.readString().toString()
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}