package com.example.doctorapp.modulePatient.presentation.auth.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val patientRepository: PatientRepository): BaseViewModel() {
    private var _patientProfileResponse: MutableLiveData<MyResponse<Patient>> = MutableLiveData()
    val patientProfileResponse get() = _patientProfileResponse

    fun getPatientProfile() {
        _patientProfileResponse.value = MyResponse.Loading
        viewModelScope.launch {
            patientRepository.getPatientProfile().let { response ->
                if(response.success == true){
                    _patientProfileResponse.value = MyResponse.Success(response.data ?: Patient())
                } else {
                    when(response.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _patientProfileResponse.value = MyResponse.Error(Exception("Unauthorized"), Define.HttpResponseCode.UNAUTHORIZED)
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _patientProfileResponse.value = MyResponse.Error(Exception(response.message ?: "Error occurred"), Define.HttpResponseCode.BAD_REQUEST)
                        }
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _patientProfileResponse.value = MyResponse.Error(Exception(response.message ?: "Error occurred"), Define.HttpResponseCode.NOT_FOUND)
                        }
                        else -> {
                            _patientProfileResponse.value = MyResponse.Error(Exception("Error occurred"), Define.HttpResponseCode.INTERNAL_SERVER_ERROR)
                        }
                    }
                }
            }
        }
    }
}