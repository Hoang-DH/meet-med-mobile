package com.example.doctorapp.modulePatient.presentation.constants

import com.google.gson.annotations.SerializedName

enum class Gender(val value: String) {
    @SerializedName("Male")
    MALE("Male"),

    @SerializedName("Female")
    FEMALE("Female"),

    @SerializedName("Other")
    OTHER("Other");

    companion object {
        fun fromString(value: String): Gender {
            return when (value) {
                "Male" -> MALE
                "Female" -> FEMALE
                else -> OTHER
            }
        }


    }

}