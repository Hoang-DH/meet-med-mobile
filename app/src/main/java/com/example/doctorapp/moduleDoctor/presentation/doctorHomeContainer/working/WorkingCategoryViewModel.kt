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

    private var _shiftListToRegister: MutableLiveData<List<DoctorShift>> = MutableLiveData()
    val shiftListToRegister: LiveData<List<DoctorShift>> get() = _shiftListToRegister

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
            _shiftListResponse.value = MyResponse.Loading
            doctorRepository.getShiftListToRegister().let { response ->
                if(response.isSuccessful){
                    _shiftListResponse.value = response.body()?.let { MyResponse.Success(it.data) }
                    _shiftListToRegister.value = response.body()?.data
                } else {
                    _shiftListResponse.value = MyResponse.Error(Exception(response.errorBody().toString()))
                }
            }
        }
    }

    fun selectAllShift(tab: String?) {
        val currentList = _shiftListToRegister.value
        if ( tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList?.forEach { it.isRegistered = true }
        }
        else if(tab == Define.WorkingTab.MY_SHIFTS){
            currentList?.forEach { it.isRegistered = false }
        }
        _shiftListToRegister.value = (currentList)
    }

//    fun clearAllShift(tab: String?) {
//        val currentList = _shiftListResponse.value as MyResponse.Success<List<DoctorShift>>
//        if (tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
//            currentList.data.forEach { it.isRegistered = false }
//        } else {
//            currentList.data.forEach { it.isRegistered = true }
//        }
//        _shiftListResponse.value = currentList
//    }

    fun selectShift(doctorShift: DoctorShift) {
        val currentList = _shiftListToRegister.value
        currentList?.find { it.id == doctorShift.id }?.isRegistered = !doctorShift.isRegistered
        _shiftListToRegister.value = (currentList)
    }

}