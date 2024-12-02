package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorAppointmentCategoryViewModel @Inject constructor(private val doctorRepository: DoctorRepository): BaseViewModel() {
    private var _appointmentResponse: MutableLiveData<MyResponse<List<DoctorAppointment>>> = MutableLiveData()
    val appointmentResponse: MutableLiveData<MyResponse<List<DoctorAppointment>>>
        get() = _appointmentResponse

    private val appointmentList: ArrayList<DoctorAppointment> = arrayListOf()

    fun getAppointments(params: Map<String, Any>) {
        if(params[Define.Fields.PAGE] == "0") {
            appointmentList.clear()
        }
        viewModelScope.launch {
            _appointmentResponse.value = MyResponse.Loading
            doctorRepository.getAppointments(params).let { response ->
                if(response.success == true){
                    appointmentList.addAll(response.data?.content ?: emptyList())
                    _appointmentResponse.value = MyResponse.Success(appointmentList)
                } else {
                    _appointmentResponse.value = MyResponse.Error(Exception(response.message), response.statusCode!!)
                }
            }
        }
    }


}