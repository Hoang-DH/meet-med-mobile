package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.model.Patient
import retrofit2.Response

interface PatientRepository {
    suspend fun getPatientRegisteredShifts(patientId: String)
    suspend fun getShiftListOfPatient()
    suspend fun registerNewShift()
    suspend fun createPatientProfile(): Response<Patient>
    suspend fun getPatientProfile(): Response<ApiResponse<Patient>>
    suspend fun updatePatientProfile(): Patient
}