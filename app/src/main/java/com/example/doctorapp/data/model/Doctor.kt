package com.example.doctorapp.data.model

data class Doctor(
    val id: Int,
    val name: String,
    val speciality: String,
    val description: String,
    val phone: String,
    val email: String,
    val website: String,
    val rating: Float,
    val reviewCount: Int,
    val imageUrl: String
)
