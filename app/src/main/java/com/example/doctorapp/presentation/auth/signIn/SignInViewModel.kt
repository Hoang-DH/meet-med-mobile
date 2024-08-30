package com.example.doctorapp.presentation.auth.signIn

import androidx.lifecycle.MutableLiveData
import com.example.doctorapp.domain.core.base.BaseViewModel

class SignInViewModel : BaseViewModel() {
    private var _validator = MutableLiveData(false)
    val validator: MutableLiveData<Boolean> get() = _validator
    private var _isEmailValid = false
    private var _isPasswordValid = false

    fun setValidState(isEmailValid: Boolean? = _isEmailValid, isPasswordValid: Boolean? = _isPasswordValid) {
        _isEmailValid = isEmailValid!!
        _isPasswordValid = isPasswordValid!!
        _validator.value = (_isEmailValid && _isPasswordValid)
    }
}