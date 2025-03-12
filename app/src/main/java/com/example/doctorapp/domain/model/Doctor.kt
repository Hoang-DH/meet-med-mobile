package com.example.doctorapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("user")
    var user: User? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("degree")
    val degree: String? = null,
    @SerializedName("yearsOfExperience")
    val yearsOfExperience: Int? = null,
    val isFavorite: Boolean = false,
    @SerializedName("numberOfPatients")
    val numberOfPatients: Int = 0,
    @SerializedName("department")
    var department: Department? = null,
    @SerializedName("dateOfBirth")
    var dob: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readParcelable(Department::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeString(description)
//        parcel.writeString(imageUrl)
        if (yearsOfExperience != null) {
            parcel.writeInt(yearsOfExperience)
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
