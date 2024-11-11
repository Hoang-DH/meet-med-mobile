package com.example.doctorapp.data.repository

import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.network.DoctorApiService
import com.example.doctorapp.utils.Response
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(private val doctorApi: DoctorApiService) : DoctorRepository {
    override suspend fun getDoctorRegisteredShifts(doctorId: String): Response<List<DoctorShift>> {
        TODO("Not yet implemented")
    }

    override suspend fun getShiftListToRegister(): Response<List<DoctorShift>> {
        return doctorApi.getShiftListToRegister()
    }

    override suspend fun getShiftListOfDoctor() {
        TODO("Not yet implemented")
    }

    override suspend fun registerNewShift(doctorShifts: List<DoctorShift>): Response<DoctorShift> {
        TODO("Not yet implemented")
    }
}