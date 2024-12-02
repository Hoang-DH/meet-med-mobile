package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailDoctorAppointmentViewModel @Inject constructor(private val doctorRepository: DoctorRepository) : BaseViewModel() {

    private val _completeAppointmentResponse = MutableLiveData<MyResponse<DoctorAppointment>>()
    val completeAppointmentResponse: MutableLiveData<MyResponse<DoctorAppointment>> = _completeAppointmentResponse

    fun completeAppointment(appointmentId: String) {
        viewModelScope.launch {
            _completeAppointmentResponse.value = MyResponse.Loading
            doctorRepository.completeAppointment(appointmentId).let { response ->
                if(response.success == true){
                    _completeAppointmentResponse.value = MyResponse.Success(response.data ?: DoctorAppointment())
                } else {
                    _completeAppointmentResponse.value = MyResponse.Error(Exception("Error"), response.statusCode!!)
                }
            }
        }
    }
}