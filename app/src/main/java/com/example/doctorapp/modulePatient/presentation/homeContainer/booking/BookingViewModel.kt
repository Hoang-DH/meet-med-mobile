package com.example.doctorapp.modulePatient.presentation.homeContainer.booking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val patientRepository: PatientRepository) :
    BaseViewModel() {

}