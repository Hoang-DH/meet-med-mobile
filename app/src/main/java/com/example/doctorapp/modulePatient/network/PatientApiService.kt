package com.example.doctorapp.modulePatient.network

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.domain.model.Department
import com.example.doctorapp.domain.model.Patient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

private const val PATH_PATIENT = "/api/patient"
private const val PATH_APPOINTMENT = "/api/appointment"
private const val PATH_DEPARTMENT = "/api/department"
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
    @JvmSuppressWildcards
    suspend fun getPatientBookedShifts(@QueryMap params: Map<String, Any>): ApiResponse<PagingResponse<BookingShift>>

    @GET(PATH_DEPARTMENT)
    suspend fun getALlDepartment(): ApiArrayResponse<Department>

    @GET("$PATH_APPOINTMENT/{appointmentId}")
    suspend fun getAppointmentById(@Path("appointmentId") id: String): ApiResponse<BookingShift>

}