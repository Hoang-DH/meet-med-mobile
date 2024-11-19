package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.model.DoctorShift
import retrofit2.Response

interface DoctorRepository {
    suspend fun getDoctorRegisteredShifts(doctorId: String): Response<List<DoctorShift>>
    suspend fun getShiftListToRegister(): Response<ApiArrayResponse<DoctorShift>>
    suspend fun getShiftListOfDoctor()
    suspend fun registerNewShift(doctorShifts: List<DoctorShift>): Response<ApiArrayResponse<DoctorShift>>
}