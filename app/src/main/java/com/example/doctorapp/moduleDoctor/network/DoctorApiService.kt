package com.example.doctorapp.moduleDoctor.network

import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.DoctorShift
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

private const val PATH_DOCTOR = "/api/doctor"

interface DoctorApiService {
    @GET("registered-shift/$PATH_DOCTOR")
    suspend fun getDoctorRegisteredShifts(
        @Query(Define.Fields.DOCTOR_ID) doctorId: String,
    ): Response<List<DoctorShift>>

    @GET("$PATH_DOCTOR/shifts/can-register")
    suspend fun getShiftListToRegister(): Response<ApiArrayResponse<DoctorShift>>

    suspend fun getShiftListOfDoctor()

    @POST("$PATH_DOCTOR/shifts/register")
    suspend fun registerNewShift(@Body doctorShifts: List<DoctorShift>): Response<ApiArrayResponse<DoctorShift>>

    @GET(PATH_DOCTOR)
    @JvmSuppressWildcards
    suspend fun searchDoctor(
        @QueryMap params: Map<String, Any>
    ): ApiResponse<PagingResponse<Doctor>>
}