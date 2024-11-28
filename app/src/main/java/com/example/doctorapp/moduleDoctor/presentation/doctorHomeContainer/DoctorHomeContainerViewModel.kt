package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer

import androidx.lifecycle.MutableLiveData
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.core.base.Event
import com.example.doctorapp.domain.core.base.SingleLiveEvent

class DoctorHomeContainerViewModel : BaseViewModel() {
    private val _navigateToDoctorWorking = SingleLiveEvent<Event<Int>>()
    val navigateToDoctorWorking: SingleLiveEvent<Event<Int>> get() = _navigateToDoctorWorking

    fun onRegisterShiftClick() {
        _navigateToDoctorWorking.value = Event(Define.DoctorBottomNav.WORKING)
        _navigateToDoctorWorking.call()
    }

}