package com.example.doctorapp.modulePatient.presentation.homeContainer.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val patientRepository: PatientRepository) :
    BaseViewModel() {
    private var _patientAppointmentResponse: MutableLiveData<MyResponse<List<BookingShift>>> =
        MutableLiveData()
    val patientAppointmentResponse: MutableLiveData<MyResponse<List<BookingShift>>>
        get() = _patientAppointmentResponse

    fun getAllPatientAppointments() {
        _patientAppointmentResponse.value = MyResponse.Loading
        viewModelScope.launch {
            patientRepository.getPatientBookedShifts().let { response ->
                if (response.success == true) {
                    _patientAppointmentResponse.value = MyResponse.Success(response.data ?: emptyList())
                } else {
                    when (response.statusCode){
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _patientAppointmentResponse.value = MyResponse.Error(Exception(response.message), Define.HttpResponseCode.UNAUTHORIZED)
                        }
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _patientAppointmentResponse.value = MyResponse.Error(Exception(response.message), Define.HttpResponseCode.NOT_FOUND)
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _patientAppointmentResponse.value = MyResponse.Error(Exception(response.message), Define.HttpResponseCode.BAD_REQUEST)
                        }
                        else -> {
                            _patientAppointmentResponse.value = MyResponse.Error(Exception(response.message), Define.HttpResponseCode.UNKNOWN)
                        }
                    }
                }

            }
        }
    }
}