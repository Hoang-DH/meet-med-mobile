package com.example.doctorapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.google.gson.annotations.SerializedName

open class User(
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
    @SerializedName("imageUrl")
    var imageUrl: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        Gender.valueOf(parcel.readString().toString()),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(fullName)
        dest.writeString(email)
        dest.writeString(gender.value)
        dest.writeString(role)
        dest.writeInt(age ?: 0)
        dest.writeString(phone)
        dest.writeString(imageUrl)
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