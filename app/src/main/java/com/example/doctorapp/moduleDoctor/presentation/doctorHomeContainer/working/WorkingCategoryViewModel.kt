package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.Define
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WorkingCategoryViewModel @Inject constructor(private val doctorRepository: DoctorRepository) : BaseViewModel() {
    private var _shiftListResponse: MutableLiveData<MyResponse<List<DoctorShift>>> = MutableLiveData()
    val shiftListResponse: LiveData<MyResponse<List<DoctorShift>>> get() = _shiftListResponse

    private var _isSelectedAll: MutableLiveData<Boolean> = MutableLiveData()
    val isSelectedAll: LiveData<Boolean> get() = _isSelectedAll

    private var _registeredShiftResponse: MutableLiveData<MyResponse<List<DoctorShift>>> = MutableLiveData()
    val registeredShiftResponse: LiveData<MyResponse<List<DoctorShift>>> get() = _registeredShiftResponse

    fun setSelectAll(isSelectAll: Boolean) {
        _isSelectedAll.value = isSelectAll
    }

    fun getListShiftToRegister() {
        viewModelScope.launch {
            _shiftListResponse.value = MyResponse.Loading
            doctorRepository.getShiftListToRegister().let { response ->
                if (response.isSuccessful) {
                    _shiftListResponse.value = MyResponse.Success(response.body()?.data ?: emptyList())
                } else {
                    when (response.body()?.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _shiftListResponse.value =
                                MyResponse.Error(Exception("Unauthorized"), Define.HttpResponseCode.UNAUTHORIZED)
                        }

                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _shiftListResponse.value = MyResponse.Error(
                                Exception(response.body()?.message ?: "Error occurred"),
                                Define.HttpResponseCode.BAD_REQUEST
                            )
                        }

                        else -> {
                            _shiftListResponse.value = MyResponse.Error(
                                Exception(response.errorBody().toString()),
                                Define.HttpResponseCode.INTERNAL_SERVER_ERROR
                            )
                        }
                    }

                }
            }
        }
    }

    fun selectAllShift(tab: String?) {
        val currentList = _shiftListResponse.value as MyResponse.Success<List<DoctorShift>>
        if (tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList.data.forEach { it.isRegistered = true }
        } else if (tab == Define.WorkingTab.MY_SHIFTS) {
            currentList.data.forEach { it.isRegistered = false }
        }
        _shiftListResponse.value = currentList
    }

    fun clearAllShift(tab: String?) {
        val currentList = _shiftListResponse.value as MyResponse.Success<List<DoctorShift>>
        if (tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList.data.forEach { it.isRegistered = false }
        } else if (tab == Define.WorkingTab.MY_SHIFTS) {
            currentList.data.forEach { it.isRegistered = true }
        }
        _shiftListResponse.value = currentList
    }

    fun selectShift(doctorShift: DoctorShift) {
        val currentList = _shiftListResponse.value as MyResponse.Success<List<DoctorShift>>
        currentList.data.find { it.id == doctorShift.id }?.isRegistered = !doctorShift.isRegistered
        _shiftListResponse.value = currentList
    }

    fun registerNewShift() {
        viewModelScope.launch {
            _registeredShiftResponse.value = MyResponse.Loading
            val currentList = _shiftListResponse.value as MyResponse.Success<List<DoctorShift>>
            val listRegisteredShift = currentList.data.filter { it.isRegistered }
            doctorRepository.registerNewShift(listRegisteredShift).let { response ->
                if (response.isSuccessful) {
                    _registeredShiftResponse.value = MyResponse.Success(currentList.data)
                } else {
                    when (response.body()?.statusCode) {
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _registeredShiftResponse.value = MyResponse.Error(Exception("Error occurred"), Define.HttpResponseCode.UNAUTHORIZED)
                        }

                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _registeredShiftResponse.value =
                                MyResponse.Error(Exception(response.body()?.message ?: "Error occurred"), Define.HttpResponseCode.BAD_REQUEST)
                        }

                        else -> {
                            _registeredShiftResponse.value =
                                MyResponse.Error(Exception(response.errorBody().toString()), Define.HttpResponseCode.INTERNAL_SERVER_ERROR)
                        }
                    }

                }
            }
        }
    }

}