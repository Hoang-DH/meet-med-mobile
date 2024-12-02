package com.example.doctorapp.modulePatient.presentation.auth.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.data.model.User
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val patientRepository: PatientRepository, private val userRepository: UserRepository, private val doctorRepository: DoctorRepository): BaseViewModel() {
    private var _patientProfileResponse: MutableLiveData<MyResponse<Patient>> = MutableLiveData()
    val patientProfileResponse get() = _patientProfileResponse

    private var _doctorProfileResponse: MutableLiveData<MyResponse<Doctor>> = MutableLiveData()
    val doctorProfileResponse get() = _doctorProfileResponse

    private var _userInfoResponse: MutableLiveData<MyResponse<User>> = MutableLiveData()
    val userInfoResponse get() = _userInfoResponse

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

    fun getDoctorProfile() {
        _doctorProfileResponse.value = MyResponse.Loading
        viewModelScope.launch {
            doctorRepository.getDoctorProfile().let { response ->
                if(response.success == true){
                    _doctorProfileResponse.value = MyResponse.Success(response.data ?: Doctor())
                } else {
                    when(response.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _doctorProfileResponse.value = MyResponse.Error(Exception("Unauthorized"), Define.HttpResponseCode.UNAUTHORIZED)
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _doctorProfileResponse.value = MyResponse.Error(Exception(response.message ?: "Error occurred"), Define.HttpResponseCode.BAD_REQUEST)
                        }
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _doctorProfileResponse.value = MyResponse.Error(Exception(response.message ?: "Error occurred"), Define.HttpResponseCode.NOT_FOUND)
                        }
                        else -> {
                            _doctorProfileResponse.value = MyResponse.Error(Exception("Error occurred"), Define.HttpResponseCode.INTERNAL_SERVER_ERROR)
                        }
                    }
                }
            }
        }
    }

    fun getUserInfo() {
        _userInfoResponse.value = MyResponse.Loading
        viewModelScope.launch {
            userRepository.getUserInfo().let { response ->
                if(response.success == true){
                    _userInfoResponse.value = MyResponse.Success(response.data ?: User())
                } else {
                    when(response.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _userInfoResponse.value = MyResponse.Error(Exception("Unauthorized"), Define.HttpResponseCode.UNAUTHORIZED)
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _userInfoResponse.value = MyResponse.Error(Exception(response.message ?: "Error occurred"), Define.HttpResponseCode.BAD_REQUEST)
                        }
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _userInfoResponse.value = MyResponse.Error(Exception(response.message ?: "Error occurred"), Define.HttpResponseCode.NOT_FOUND)
                        }
                        else -> {
                            _userInfoResponse.value = MyResponse.Error(Exception("Error occurred"), Define.HttpResponseCode.INTERNAL_SERVER_ERROR)
                        }
                    }
                }
            }
        }
    }
}