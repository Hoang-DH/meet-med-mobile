package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.utils.Define
import com.example.doctorapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkingCategoryViewModel @Inject constructor(private val doctorRepository: DoctorRepository) : BaseViewModel() {
    private var _shiftListResponse: MutableLiveData<Response<List<DoctorShift>>> = MutableLiveData()
    val shiftListResponse: LiveData<Response<List<DoctorShift>>> get() = _shiftListResponse

    private var _isSelectedAll: MutableLiveData<Boolean> = MutableLiveData()
    val isSelectedAll: LiveData<Boolean> get() = _isSelectedAll

    private var _isLoadingComplete = MutableLiveData(false)
    val isLoadingComplete: LiveData<Boolean> get() = _isLoadingComplete

    fun setSelectAll(isSelectAll: Boolean) {
        _isSelectedAll.value = isSelectAll
    }

//    fun generateShifts() {
//        val shifts = mutableListOf<DoctorShift>()
//        val startDate = LocalDate.now().plusDays(1)
//        val endDate = startDate.plusDays(6)
//        var currentDate = startDate
//        var idCounter = 1
//
//        while (!currentDate.isAfter(endDate)) {
//            val morningShiftStart = LocalDateTime.of(currentDate, LocalTime.of(8, 0)).atZone(ZoneId.systemDefault()).toInstant()
//            val morningShiftEnd = LocalDateTime.of(currentDate, LocalTime.of(12, 0)).atZone(ZoneId.systemDefault()).toInstant()
//            val afternoonShiftStart = LocalDateTime.of(currentDate, LocalTime.of(13, 0)).atZone(ZoneId.systemDefault()).toInstant()
//            val afternoonShiftEnd = LocalDateTime.of(currentDate, LocalTime.of(17, 0)).atZone(ZoneId.systemDefault()).toInstant()
//
//            shifts.add(DoctorShift(idCounter++, morningShiftStart, morningShiftEnd))
//            shifts.add(DoctorShift(idCounter++, afternoonShiftStart, afternoonShiftEnd))
//
//            currentDate = currentDate.plusDays(1)
//        }
//
//        _shiftListResponse.value = shifts
//    }

    fun getListShiftToRegister() {
        viewModelScope.launch {
            _shiftListResponse.value = Response.Loading
            _shiftListResponse.value = doctorRepository.getShiftListToRegister()
            checkLoadingComplete()
        }
    }

    fun selectAllShift(tab: String?) {
        val currentList = _shiftListResponse.value as Response.Success<List<DoctorShift>>
        if(tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList.data.forEach { it.isRegistered = true }
        } else {
            currentList.data.forEach { it.isRegistered = false }
        }
        _shiftListResponse.value = (currentList)
    }
//
    fun clearAllShift(tab: String?) {
        val currentList = _shiftListResponse.value as Response.Success<List<DoctorShift>>
        if (tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList.data.forEach { it.isRegistered = false }
        } else {
            currentList.data.forEach { it.isRegistered = true }
        }
        _shiftListResponse.value = (currentList)
    }
//
    fun selectShift(doctorShift: DoctorShift) {
        val currentList = _shiftListResponse.value as Response.Success<List<DoctorShift>>
        currentList.data.find { it.id == doctorShift.id }?.isRegistered = !doctorShift.isRegistered
        _shiftListResponse.value = (currentList)
    }

    private fun checkLoadingComplete() {
        _isLoadingComplete.value = (_shiftListResponse.value is Response.Success || _shiftListResponse.value is Response.Error)
    }
}