package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.profile.editProfile


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorEditProfileViewModel @Inject constructor(private val doctorRepository: DoctorRepository): BaseViewModel() {

    private var _doctorProfileResponse: MutableLiveData<MyResponse<Doctor>> = MutableLiveData()
    val doctorProfileResponse: MutableLiveData<MyResponse<Doctor>>
        get() = _doctorProfileResponse

    fun getDoctorProfile(){
        _doctorProfileResponse.value = MyResponse.Loading
        viewModelScope.launch {
            doctorRepository.getDoctorProfile().let { response ->
                if(response.success == true){
                    _doctorProfileResponse.value = MyResponse.Success(response.data ?: Doctor())
                } else {
                    _doctorProfileResponse.value = MyResponse.Error(Exception("Error"), response.statusCode!!)
                }
            }
        }
    }

    fun updateDoctorProfile(doctor: Doctor){
        _doctorProfileResponse.value = MyResponse.Loading
        viewModelScope.launch {
            doctorRepository.updateDoctorProfile(doctor).let { response ->
                if(response.success == true){
                    _doctorProfileResponse.value = MyResponse.Success(response.data ?: Doctor())
                } else {
                    _doctorProfileResponse.value = MyResponse.Error(Exception("Error"), response.statusCode!!)
                }
            }
        }
    }

}