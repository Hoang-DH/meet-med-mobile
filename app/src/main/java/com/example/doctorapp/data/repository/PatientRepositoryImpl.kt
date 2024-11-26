package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.BookingShiftDTO
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.modulePatient.network.PatientApiService
import com.example.doctorapp.utils.MyResponse
import retrofit2.HttpException
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

    override suspend fun createPatientProfile(patient: Patient): ApiResponse<Patient> {
        return patientApi.createPatientProfile(patient)
    }

    override suspend fun getPatientProfile(): ApiResponse<Patient> {
        return patientApi.getPatientProfile()

    }

    override suspend fun updatePatientProfile(patient: Patient): ApiResponse<Patient> {
        return patientApi.updatePatientProfile(patient)
    }

    override suspend fun bookingAppointment(bookingShiftDTO: BookingShiftDTO): ApiResponse<BookingShiftDTO> {
        return patientApi.bookingAppointment(bookingShiftDTO)
    }

}