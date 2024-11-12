package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.DoctorShiftToRegister
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.network.DoctorApiService
import retrofit2.Response
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(private val doctorApi: DoctorApiService) : DoctorRepository {
    override suspend fun getDoctorRegisteredShifts(doctorId: String): Response<List<DoctorShift>> {
        TODO("Not yet implemented")
    }

    override suspend fun getShiftListToRegister(): Response<DoctorShiftToRegister> {
        return doctorApi.getShiftListToRegister()
    }

    override suspend fun getShiftListOfDoctor() {
        TODO("Not yet implemented")
    }

    override suspend fun registerNewShift(doctorShifts: List<DoctorShift>): Response<DoctorShift> {
        TODO("Not yet implemented")
    }
}