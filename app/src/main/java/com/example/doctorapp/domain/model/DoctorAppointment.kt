package com.example.doctorapp.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DoctorAppointment(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("symptoms")
    var symptoms: String? = null,
    @SerializedName("registeredShiftTimeSlot")
    var timeSlot: TimeSlot? = null,
    @SerializedName("doctor")
    var doctor: Doctor? = null,
    @SerializedName("patient")
    var patient: Patient? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(TimeSlot::class.java.classLoader),
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readParcelable(Patient::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(symptoms)
        parcel.writeParcelable(doctor, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookingShift> {
        override fun createFromParcel(parcel: Parcel): BookingShift {
            return BookingShift(parcel)
        }

        override fun newArray(size: Int): Array<BookingShift?> {
            return arrayOfNulls(size)
        }
    }
}