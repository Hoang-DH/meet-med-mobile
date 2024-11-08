package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.utils.Response

interface DoctorRepository {
    suspend fun getDoctorRegisteredShifts(doctorId: String): Response<List<DoctorShift>>
    suspend fun getShiftListToRegister(): Response<List<DoctorShift>>
    suspend fun getShiftListOfDoctor()
    suspend fun registerNewShift(doctorShifts: List<DoctorShift>): Response<DoctorShift>
}