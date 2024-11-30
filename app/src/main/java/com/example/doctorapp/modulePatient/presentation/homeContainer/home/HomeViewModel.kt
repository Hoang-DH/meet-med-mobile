package com.example.doctorapp.modulePatient.presentation.homeContainer.home

import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    fun postFCMDeviceToken(fcm: Fcm) {
        viewModelScope.launch {
            userRepository.postFCMDeviceToken(fcm)
        }
    }
}