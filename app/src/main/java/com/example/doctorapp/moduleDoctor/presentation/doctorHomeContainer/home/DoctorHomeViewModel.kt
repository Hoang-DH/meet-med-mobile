package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.domain.model.DoctorAppointment
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorHomeViewModel @Inject constructor(private val doctorRepository: DoctorRepository, private val userRepository: UserRepository) : BaseViewModel() {
    private var _appointmentResponse = MutableLiveData<MyResponse<List<DoctorAppointment>>>()
    val appointmentResponse: MutableLiveData<MyResponse<List<DoctorAppointment>>>
        get() = _appointmentResponse

    fun getAppointments(params: Map<String, Any>) {
        viewModelScope.launch {
            _appointmentResponse.value = MyResponse.Loading
            doctorRepository.getAppointments(params).let { response ->
                if(response.success == true){
                    val todayAppointment: List<DoctorAppointment> = filterOnlyTodayAppointments(response.data?.content ?: emptyList())
                    _appointmentResponse.value = MyResponse.Success(todayAppointment)
                } else {
                    _appointmentResponse.value = MyResponse.Error(Exception(response.message), response.statusCode!!)
                }
            }
        }
    }

    private fun filterOnlyTodayAppointments(appointments: List<DoctorAppointment>): List<DoctorAppointment> {
        return appointments.filter { DateUtils.isToday(it.timeSlot?.startTime) }
    }

    fun postFCMDeviceToken(fcm: Fcm) {
        viewModelScope.launch {
            userRepository.postFCMDeviceToken(fcm)
        }
    }
}