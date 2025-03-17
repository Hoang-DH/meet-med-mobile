package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiArrayResponse
import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.domain.model.Department
import com.example.doctorapp.domain.model.Patient

interface PatientRepository {
    suspend fun getPatientRegisteredShifts(patientId: String)
    suspend fun getShiftListOfPatient()
    suspend fun registerNewShift()
    suspend fun createPatientProfile(patient: Patient): ApiResponse<Patient>
    suspend fun getPatientProfile(): ApiResponse<Patient>
    suspend fun updatePatientProfile(patient: Patient): ApiResponse<Patient>
    suspend fun bookingAppointment(bookingShift: BookingShift): ApiResponse<BookingShift>
    suspend fun getPatientBookedShifts(params: Map<String, Any>): ApiResponse<PagingResponse<BookingShift>>
    suspend fun getALlDepartment(): ApiArrayResponse<Department>
    suspend fun getAppointmentById(id: String): ApiResponse<BookingShift>
}