package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.utils.Define
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId


class WorkingCategoryViewModel : BaseViewModel() {
    private var _shiftList: MutableLiveData<List<DoctorShift>> = MutableLiveData()
    val shiftList: LiveData<List<DoctorShift>> get() = _shiftList

    private var _isSelectedAll: MutableLiveData<Boolean> = MutableLiveData()
    val isSelectedAll: LiveData<Boolean> get() = _isSelectedAll


    fun setSelectAll(isSelectAll: Boolean) {
        _isSelectedAll.value = isSelectAll
    }

    fun generateShifts() {
        val shifts = mutableListOf<DoctorShift>()
        val startDate = LocalDate.now().plusDays(1)
        val endDate = startDate.plusDays(6)
        var currentDate = startDate
        var idCounter = 1

        while (!currentDate.isAfter(endDate)) {
            val morningShiftStart = LocalDateTime.of(currentDate, LocalTime.of(8, 0)).atZone(ZoneId.systemDefault()).toInstant()
            val morningShiftEnd = LocalDateTime.of(currentDate, LocalTime.of(12, 0)).atZone(ZoneId.systemDefault()).toInstant()
            val afternoonShiftStart = LocalDateTime.of(currentDate, LocalTime.of(13, 0)).atZone(ZoneId.systemDefault()).toInstant()
            val afternoonShiftEnd = LocalDateTime.of(currentDate, LocalTime.of(17, 0)).atZone(ZoneId.systemDefault()).toInstant()

            shifts.add(DoctorShift(idCounter++, morningShiftStart, morningShiftEnd, false))
            shifts.add(DoctorShift(idCounter++, afternoonShiftStart, afternoonShiftEnd, false))

            currentDate = currentDate.plusDays(1)
        }

        _shiftList.value = shifts
    }

    fun selectAllShift(tab: String?) {
        val currentList = _shiftList.value
        if(tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList?.forEach { it.isRegistered = true }
        } else {
            currentList?.forEach { it.isRegistered = false }
        }
        _shiftList.postValue(currentList!!)
    }

    fun clearAllShift(tab: String?) {
        val currentList = _shiftList.value
        if (tab == Define.WorkingTab.REGISTER_NEW_SHIFT) {
            currentList?.forEach { it.isRegistered = false }
        } else {
            currentList?.forEach { it.isRegistered = true }
        }
        _shiftList.postValue(currentList!!)
    }

    fun selectShift(doctorShift: DoctorShift) {
        val currentList = _shiftList.value
        currentList?.find { it.id == doctorShift.id }?.isRegistered = !doctorShift.isRegistered
        _shiftList.postValue(currentList!!)
    }
}