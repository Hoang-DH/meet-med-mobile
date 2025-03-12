package com.example.doctorapp.moduleDoctor.network

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.domain.model.Doctor
import com.example.doctorapp.domain.model.DoctorAppointment
import com.example.doctorapp.domain.model.DoctorBookingShift
import com.example.doctorapp.domain.model.DoctorShift
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

private const val PATH_DOCTOR = "/api/doctor"

interface DoctorApiService {

    @GET("$PATH_DOCTOR/shifts/can-register")
    suspend fun getShiftListToRegister(): Response<ApiArrayResponse<DoctorShift>>

    @POST("$PATH_DOCTOR/shifts/register")
    suspend fun registerNewShift(@Body doctorShifts: List<DoctorShift>): Response<ApiArrayResponse<DoctorShift>>

    @GET(PATH_DOCTOR)
    @JvmSuppressWildcards
    suspend fun searchDoctor(
        @QueryMap params: Map<String, Any>
    ): ApiResponse<PagingResponse<Doctor>>

    @GET("$PATH_DOCTOR/{doctorId}/booking-shifts")
    suspend fun getDoctorBookingShifts(@Path("doctorId") doctorId: String): ApiArrayResponse<DoctorBookingShift>

    @GET("$PATH_DOCTOR/shifts/get-week")
    suspend fun getRegisteredShifts(): Response<ApiArrayResponse<DoctorShift>>

    @GET("$PATH_DOCTOR/appointment")
    @JvmSuppressWildcards
    suspend fun getAppointments(@QueryMap params: Map<String, Any>) : ApiResponse<PagingResponse<DoctorAppointment>>

    @PUT("$PATH_DOCTOR/appointment/{appointmentId}/complete")
    suspend fun completeAppointment(@Path("appointmentId") appointmentId: String): ApiResponse<DoctorAppointment>

    @GET("$PATH_DOCTOR/my-profile")
    suspend fun getDoctorProfile(): ApiResponse<Doctor>

    @PUT("$PATH_DOCTOR/my-profile")
    suspend fun updateDoctorProfile(@Body doctor: Doctor): ApiResponse<Doctor>

}