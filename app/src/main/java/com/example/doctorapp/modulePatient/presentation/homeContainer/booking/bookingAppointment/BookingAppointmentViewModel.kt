package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingAppointment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.data.model.DoctorBookingShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingAppointmentViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository,
    private val patientRepository: PatientRepository
) : BaseViewModel() {

    private var _doctorBookingShiftResponse: MutableLiveData<MyResponse<List<DoctorBookingShift>>> = MutableLiveData()
    val doctorBookingShiftLiveData: MutableLiveData<MyResponse<List<DoctorBookingShift>>>
        get() = _doctorBookingShiftResponse

    private var _bookingAppointmentResponse: MutableLiveData<MyResponse<BookingShift>> = MutableLiveData()
    val bookingAppointmentResponse: MutableLiveData<MyResponse<BookingShift>>
        get() = _bookingAppointmentResponse


    fun getDoctorBookingShifts(doctorId: String) {
        _doctorBookingShiftResponse.value = MyResponse.Loading
        viewModelScope.launch {
            doctorRepository.getDoctorBookingShifts(doctorId).let { response ->
                if (response.success == true) {
                    _doctorBookingShiftResponse.value = MyResponse.Success(response.data ?: emptyList())
                } else {
                    when (response.statusCode) {
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _doctorBookingShiftResponse.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.NOT_FOUND
                            )
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _doctorBookingShiftResponse.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.BAD_REQUEST
                            )
                        }
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _doctorBookingShiftResponse.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.UNAUTHORIZED
                            )
                        }
                        else -> {
                            _doctorBookingShiftResponse.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.INTERNAL_SERVER_ERROR
                            )
                        }
                    }
                }
            }
        }
    }

    fun bookAppointment(bookingShift: BookingShift){
        _bookingAppointmentResponse.value = MyResponse.Loading
        viewModelScope.launch {
            patientRepository.bookingAppointment(bookingShift).let { response ->
                if (response.success == true) {
                    _bookingAppointmentResponse.value = MyResponse.Success(response.data ?: BookingShift())
                } else {
                    when (response.statusCode) {
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _bookingAppointmentResponse.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.NOT_FOUND
                            )
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _bookingAppointmentResponse.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.BAD_REQUEST
                            )
                        }
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _bookingAppointmentResponse.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.UNAUTHORIZED
                            )
                        }
                        else -> {
                            _bookingAppointmentResponse.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.INTERNAL_SERVER_ERROR
                            )
                        }
                    }
                }
            }
        }
    }

}