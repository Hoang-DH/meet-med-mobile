package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.modulePatient.network.PatientApiService
import retrofit2.Response
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(private val patientApi: PatientApiService): PatientRepository {
    override suspend fun getPatientRegisteredShifts(patientId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getShiftListOfPatient() {
        TODO("Not yet implemented")
    }

    override suspend fun registerNewShift() {
        TODO("Not yet implemented")
    }

    override suspend fun createPatientProfile(): Response<Patient> {
        return patientApi.createPatientProfile()
    }

    override suspend fun getPatientProfile(): Response<ApiResponse<Patient>> {
        return patientApi.getPatientProfile()
    }

    override suspend fun updatePatientProfile(): Patient {
        TODO("Not yet implemented")
    }

}