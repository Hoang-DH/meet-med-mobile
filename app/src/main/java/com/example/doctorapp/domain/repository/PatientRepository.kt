package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PatientDTO
import com.example.doctorapp.data.model.Patient
import retrofit2.Response

interface PatientRepository {
    suspend fun getPatientRegisteredShifts(patientId: String)
    suspend fun getShiftListOfPatient()
    suspend fun registerNewShift()
    suspend fun createPatientProfile(patientDTO: PatientDTO): ApiResponse<Patient>
    suspend fun getPatientProfile(): ApiResponse<Patient>
    suspend fun updatePatientProfile(patientDTO: PatientDTO): ApiResponse<Patient>
}