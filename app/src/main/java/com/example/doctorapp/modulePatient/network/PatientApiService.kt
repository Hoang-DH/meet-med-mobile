package com.example.doctorapp.modulePatient.network

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.utils.MyResponse
import retrofit2.Response
import retrofit2.http.GET

private const val PATH_PATIENT = "/api/patient"
interface PatientApiService {

    @GET("$PATH_PATIENT/profile")
    suspend fun getPatientProfile(): ApiResponse<Patient>

    suspend fun createPatientProfile(): ApiResponse<Patient>

    suspend fun updatePatientProfile()
}