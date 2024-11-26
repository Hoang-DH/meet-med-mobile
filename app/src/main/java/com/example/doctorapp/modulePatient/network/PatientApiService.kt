package com.example.doctorapp.modulePatient.network

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.data.model.Patient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

private const val PATH_PATIENT = "/api/patient"
private const val PATH_APPOINTMENT = "/api/appointment"
interface PatientApiService {

    @GET("$PATH_PATIENT/profile")
    suspend fun getPatientProfile(): ApiResponse<Patient>

    @POST("$PATH_PATIENT/profile")
    suspend fun createPatientProfile(patient: Patient): ApiResponse<Patient>

    @PUT("$PATH_PATIENT/profile")
    suspend fun updatePatientProfile(@Body patient: Patient): ApiResponse<Patient>

    @POST(PATH_APPOINTMENT)
    suspend fun bookingAppointment(@Body bookingShift: BookingShift): ApiResponse<BookingShift>

    @GET(PATH_APPOINTMENT)
    suspend fun getPatientBookedShifts(): ApiArrayResponse<BookingShift>
}