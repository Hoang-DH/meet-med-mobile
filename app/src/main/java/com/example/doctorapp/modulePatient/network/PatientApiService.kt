package com.example.doctorapp.modulePatient.network

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PatientDTO
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.utils.MyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

private const val PATH_PATIENT = "/api/patient"
interface PatientApiService {

    @GET("$PATH_PATIENT/profile")
    suspend fun getPatientProfile(): ApiResponse<Patient>

    @POST("$PATH_PATIENT/profile")
    suspend fun createPatientProfile(): ApiResponse<Patient>

    @PUT("$PATH_PATIENT/profile")
    suspend fun updatePatientProfile(@Body patientDTO: PatientDTO): ApiResponse<Patient>
}