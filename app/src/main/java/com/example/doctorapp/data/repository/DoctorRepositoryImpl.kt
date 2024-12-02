package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.data.model.DoctorBookingShift
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.moduleDoctor.network.DoctorApiService
import retrofit2.Response
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(private val doctorApi: DoctorApiService) : DoctorRepository {
    override suspend fun getDoctorRegisteredShifts(doctorId: String): Response<List<DoctorShift>> {
        TODO("Not yet implemented")
    }

    override suspend fun getShiftListToRegister(): Response<ApiArrayResponse<DoctorShift>> {
        return doctorApi.getShiftListToRegister()
    }

    override suspend fun getShiftListOfDoctor() {
        TODO("Not yet implemented")
    }

    override suspend fun registerNewShift(doctorShifts: List<DoctorShift>): Response<ApiArrayResponse<DoctorShift>> {
        return doctorApi.registerNewShift(doctorShifts)
    }

    override suspend fun searchDoctor(
        params: Map<String, Any>
    ): ApiResponse<PagingResponse<Doctor>> {
        return doctorApi.searchDoctor(params)
    }

    override suspend fun getDoctorBookingShifts(doctorId: String): ApiArrayResponse<DoctorBookingShift> {
        return doctorApi.getDoctorBookingShifts(doctorId)
    }

    override suspend fun getRegisteredShifts(): Response<ApiArrayResponse<DoctorShift>> {
        return doctorApi.getRegisteredShifts()
    }

    override suspend fun getAppointments(params: Map<String, Any>): ApiResponse<PagingResponse<DoctorAppointment>> {
        return doctorApi.getAppointments(params)
    }

    override suspend fun completeAppointment(appointmentId: String): ApiResponse<DoctorAppointment> {
        return doctorApi.completeAppointment(appointmentId)
    }

    override suspend fun getDoctorProfile(): ApiResponse<Doctor> {
        return doctorApi.getDoctorProfile()
    }

    override suspend fun updateDoctorProfile(doctor: Doctor): ApiResponse<Doctor> {
        return doctorApi.updateDoctorProfile(doctor)
    }
}