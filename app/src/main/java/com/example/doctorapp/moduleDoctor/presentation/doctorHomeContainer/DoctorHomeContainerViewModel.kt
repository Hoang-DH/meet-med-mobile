package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer

import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.core.base.Event
import com.example.doctorapp.domain.core.base.SingleLiveEvent

class DoctorHomeContainerViewModel : BaseViewModel() {
    private val _navigateToDoctorWorking = SingleLiveEvent<Event<Int>>()
    val navigateToDoctorWorking: SingleLiveEvent<Event<Int>> get() = _navigateToDoctorWorking

    fun onFunctionClick(tabId: Int) {
        _navigateToDoctorWorking.value = Event(tabId)
    }

}