package com.example.doctorapp.moduleDoctor.network

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.model.DoctorShift
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val PATH_DOCTOR = "/api/doctor"
interface DoctorApiService {
    @GET("registered-shift/$PATH_DOCTOR")
    suspend fun getDoctorRegisteredShifts(
        @Query("doctorId") doctorId: String,
    ): Response<List<DoctorShift>>

    @GET("$PATH_DOCTOR/shifts/can-register")
    suspend fun getShiftListToRegister(): Response<ApiArrayResponse<DoctorShift>>

    suspend fun getShiftListOfDoctor()

    @POST("$PATH_DOCTOR/shifts/register")
    suspend fun registerNewShift(@Body doctorShifts: List<DoctorShift>): Response<ApiArrayResponse<DoctorShift>>
}