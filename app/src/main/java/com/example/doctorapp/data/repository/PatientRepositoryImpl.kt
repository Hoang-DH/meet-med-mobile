package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.domain.model.Department
import com.example.doctorapp.domain.model.Patient
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.modulePatient.network.PatientApiService
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

    override suspend fun bookingAppointment(bookingShift: BookingShift): ApiResponse<BookingShift> {
        return patientApi.bookingAppointment(bookingShift)
    }

    override suspend fun getPatientBookedShifts(params: Map<String, Any>): ApiResponse<PagingResponse<BookingShift>> {
        return patientApi.getPatientBookedShifts(params)
    }

    override suspend fun getALlDepartment(): ApiArrayResponse<Department> {
        return patientApi.getALlDepartment()
    }

    override suspend fun getAppointmentById(id: String): ApiResponse<BookingShift> {
        return patientApi.getAppointmentById(id)
    }
}