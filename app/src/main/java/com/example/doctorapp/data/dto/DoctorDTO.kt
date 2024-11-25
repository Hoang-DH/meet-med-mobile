package com.example.doctorapp.data.dto

import com.example.doctorapp.data.model.Department

data class DoctorDTO(
    var id: String? = null,
    var yearOfExperience: Int? = null,
    var degree: String? = null,
    var description: String? = null,
    var user: UserDTO? = null,
    var department: Department? = null,
)