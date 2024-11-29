package com.example.doctorapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class DoctorAppointment(
    var id: String? = null,
    var patientName: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var timeSlot: TimeSlot? = null,
    var symtom: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("timeSlot"),
        parcel.readString()
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<DoctorAppointment> {
        override fun createFromParcel(parcel: Parcel): DoctorAppointment {
            return DoctorAppointment(parcel)
        }

        override fun newArray(size: Int): Array<DoctorAppointment?> {
            return arrayOfNulls(size)
        }
    }
}
