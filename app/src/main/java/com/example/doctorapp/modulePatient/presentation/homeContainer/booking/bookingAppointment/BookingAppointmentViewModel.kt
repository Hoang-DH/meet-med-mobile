package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingAppointment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.DoctorBookingShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingAppointmentViewModel @Inject constructor(private val doctorRepository: DoctorRepository): BaseViewModel() {

    private var _doctorBookingShiftResponse: MutableLiveData<MyResponse<List<DoctorBookingShift>>> = MutableLiveData()
    val doctorBookingShiftLiveData: MutableLiveData<MyResponse<List<DoctorBookingShift>>>
        get() = _doctorBookingShiftResponse


    fun getDoctorBookingShifts(doctorId: String) {
        _doctorBookingShiftResponse.value = MyResponse.Loading
        viewModelScope.launch {
            doctorRepository.getDoctorBookingShifts(doctorId).let { response ->
                if(response.success == true){
                    _doctorBookingShiftResponse.value = MyResponse.Success(response.data ?: emptyList())
                } else {
                    when(response.statusCode){
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

}