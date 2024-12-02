package com.example.doctorapp.modulePatient.presentation.homeContainer.booking

import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(private val patientRepository: PatientRepository) :
    BaseViewModel() {

}