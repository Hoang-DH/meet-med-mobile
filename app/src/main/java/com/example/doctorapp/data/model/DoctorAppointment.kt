package com.example.doctorapp.data.model

data class DoctorAppointment(
    var id: String? = null,
    var patientName: String? = null,
    var phone: String? = null,
    var email: String? = null,
    var timeSlot: TimeSlot? = null,
    var symtom: String? = null
)
