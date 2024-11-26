package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.BookingShiftDTO
import com.example.doctorapp.data.model.Patient
import retrofit2.Response

interface PatientRepository {
    suspend fun getPatientRegisteredShifts(patientId: String)
    suspend fun getShiftListOfPatient()
    suspend fun registerNewShift()
    suspend fun createPatientProfile(patient: Patient): ApiResponse<Patient>
    suspend fun getPatientProfile(): ApiResponse<Patient>
    suspend fun updatePatientProfile(patient: Patient): ApiResponse<Patient>
    suspend fun bookingAppointment(bookingShiftDTO: BookingShiftDTO): ApiResponse<BookingShiftDTO>

}