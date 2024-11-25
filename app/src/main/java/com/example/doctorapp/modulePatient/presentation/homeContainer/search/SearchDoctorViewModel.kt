package com.example.doctorapp.modulePatient.presentation.homeContainer.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDoctorViewModel @Inject constructor(private val doctorRepository: DoctorRepository) : BaseViewModel() {
    private var _searchDoctorResponse: MutableLiveData<MyResponse<List<Doctor>>> = MutableLiveData()
    val searchDoctorResponse: MutableLiveData<MyResponse<List<Doctor>>>
        get() = _searchDoctorResponse

    fun searchDoctor(params: Map<String, Any>) {
        _searchDoctorResponse.value = MyResponse.Loading
        viewModelScope.launch {
            doctorRepository.searchDoctor(params).let { response ->
                if (response.success == true) {
                    _searchDoctorResponse.value = MyResponse.Success(response.data?.content ?: emptyList())
                } else {
                    when (response.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _searchDoctorResponse.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.UNAUTHORIZED
                            )
                        }

                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _searchDoctorResponse.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.BAD_REQUEST
                            )
                        }

                        Define.HttpResponseCode.NOT_FOUND -> {
                            _searchDoctorResponse.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.NOT_FOUND
                            )
                        }

                        else -> {
                            _searchDoctorResponse.value = MyResponse.Error(
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