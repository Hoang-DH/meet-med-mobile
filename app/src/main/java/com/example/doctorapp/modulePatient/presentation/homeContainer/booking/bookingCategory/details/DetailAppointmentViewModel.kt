package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAppointmentViewModel @Inject constructor(private val patientRepository: PatientRepository): BaseViewModel() {
    private var _appointmentResponse = MutableLiveData<MyResponse<BookingShift>>()
    val appointmentResponse: MutableLiveData<MyResponse<BookingShift>> get() = _appointmentResponse

    fun getAppointmentById(id: String) {
        viewModelScope.launch {
            patientRepository.getAppointmentById(id).let { response ->
               if(response.success == true) {
                   _appointmentResponse.value = MyResponse.Success(response.data ?: BookingShift())
               } else {
                   _appointmentResponse.value = MyResponse.Error(Exception(response.message), response.statusCode!!)
               }
            }
        }
    }

}