package com.example.doctorapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.core.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    private val _actionSplash = MutableLiveData<SplashActionState>()
    val actionSplash: LiveData<SplashActionState> = _actionSplash
    init {
        viewModelScope.launch {
            delay(1000)
            _actionSplash.value = SplashActionState.Finish
        }
    }
}

sealed class SplashActionState{
    data object Finish: SplashActionState()
}