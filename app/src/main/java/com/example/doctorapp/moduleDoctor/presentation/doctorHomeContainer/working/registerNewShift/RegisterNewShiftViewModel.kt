package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working.registerNewShift

import androidx.lifecycle.MutableLiveData
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class RegisterNewShiftViewModel : BaseViewModel() {
    private var _shiftList: MutableLiveData<List<DoctorShift>> = MutableLiveData()
    val shiftList: MutableLiveData<List<DoctorShift>> get() = _shiftList

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
}