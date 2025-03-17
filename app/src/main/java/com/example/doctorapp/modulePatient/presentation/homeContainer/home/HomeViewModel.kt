package com.example.doctorapp.modulePatient.presentation.homeContainer.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.domain.model.Department
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository, private val patientRepository: PatientRepository) : BaseViewModel() {

    private var _departmentResponse: MutableLiveData<MyResponse<List<Department>>> = MutableLiveData()
    val departmentResponse: MutableLiveData<MyResponse<List<Department>>>
        get() = _departmentResponse

    fun getAllDepartment(){
        viewModelScope.launch {
            patientRepository.getALlDepartment().let { response ->
                if(response.success == true){
                    _departmentResponse.value = MyResponse.Success(response.data ?: emptyList())
                } else {
                    when(response.statusCode){
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _departmentResponse.value = MyResponse.Error(Exception("Unauthorized"), Define.HttpResponseCode.UNAUTHORIZED)
                        }
                        else -> {
                            _departmentResponse.value = MyResponse.Error(Exception("Error occurred"), Define.HttpResponseCode.UNKNOWN)
                        }
                    }

                }
            }
        }
    }

    fun postFCMDeviceToken(fcm: Fcm) {
        viewModelScope.launch {
            userRepository.postFCMDeviceToken(fcm)
        }
    }
}