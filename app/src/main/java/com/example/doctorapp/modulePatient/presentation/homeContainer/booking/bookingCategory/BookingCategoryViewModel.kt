package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingCategoryViewModel @Inject constructor(private val patientRepository: PatientRepository) : BaseViewModel() {
    private var _patientAppointmentResponse: MutableLiveData<MyResponse<List<BookingShift>>> =
        MutableLiveData()
    val patientAppointmentResponse: MutableLiveData<MyResponse<List<BookingShift>>>
        get() = _patientAppointmentResponse

    private val patientAppointmentList: ArrayList<BookingShift> = arrayListOf()

    fun getAllPatientAppointments(params: Map<String, Any> = emptyMap()) {
        if (params[Define.Fields.PAGE] == "0") {
            patientAppointmentList.clear()
        }
        _patientAppointmentResponse.value = MyResponse.Loading
        viewModelScope.launch {
            patientRepository.getPatientBookedShifts(params).let { response ->
                if (response.success == true) {
                    patientAppointmentList.addAll(response.data?.content ?: emptyList())
                    _patientAppointmentResponse.value = MyResponse.Success(patientAppointmentList)
                } else {
                    when (response.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _patientAppointmentResponse.value =
                                MyResponse.Error(Exception(response.message), Define.HttpResponseCode.UNAUTHORIZED)
                        }

                        Define.HttpResponseCode.NOT_FOUND -> {
                            _patientAppointmentResponse.value =
                                MyResponse.Error(Exception(response.message), Define.HttpResponseCode.NOT_FOUND)
                        }

                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _patientAppointmentResponse.value =
                                MyResponse.Error(Exception(response.message), Define.HttpResponseCode.BAD_REQUEST)
                        }

                        else -> {
                            _patientAppointmentResponse.value =
                                MyResponse.Error(Exception(response.message), Define.HttpResponseCode.UNKNOWN)
                        }
                    }
                }

            }
        }
    }
}