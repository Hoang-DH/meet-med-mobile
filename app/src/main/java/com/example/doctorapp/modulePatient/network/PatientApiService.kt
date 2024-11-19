package com.example.doctorapp.modulePatient.network

import com.example.doctorapp.data.model.Patient
import retrofit2.Response
import retrofit2.http.GET

private const val PATH_PATIENT = "/api/patient"
interface PatientApiService {

    @GET("$PATH_PATIENT/profile")
    suspend fun getPatientProfile(): Response<Patient>

    suspend fun createPatientProfile(): Response<Patient>

    suspend fun updatePatientProfile()
}