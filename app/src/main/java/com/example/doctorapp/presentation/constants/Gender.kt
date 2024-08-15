package com.example.doctorapp.presentation.constants

enum class Gender(val value: String) {
    MALE("Male"),
    FEMALE("Female"),
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