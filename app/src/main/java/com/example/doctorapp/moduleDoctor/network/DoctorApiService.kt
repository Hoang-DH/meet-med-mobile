package com.example.doctorapp.moduleDoctor.network

import com.example.doctorapp.constant.Define
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
        @Query(Define.Fields.DOCTOR_ID) doctorId: String,
    ): Response<List<DoctorShift>>

    @GET("$PATH_DOCTOR/shifts/can-register")
    suspend fun getShiftListToRegister(): Response<ApiArrayResponse<DoctorShift>>

    suspend fun getShiftListOfDoctor()

    @POST("$PATH_DOCTOR/shifts/register")
    suspend fun registerNewShift(@Body doctorShifts: List<DoctorShift>): Response<ApiArrayResponse<DoctorShift>>

    @GET(PATH_DOCTOR)
    suspend fun searchDoctor(
        @Query(Define.Fields.PAGE) page: Int,
        @Query(Define.Fields.SIZE) size: Int,
        @Query(Define.Fields.ORDER) order: String,
        @Query(Define.Fields.ORDER_BY) orderBy: String,
        @Query(Define.Fields.NAME) name: String,
        @Query(Define.Fields.DEPARTMENT) department: String,
    ): Response<ApiArrayResponse<DoctorShift>>
}