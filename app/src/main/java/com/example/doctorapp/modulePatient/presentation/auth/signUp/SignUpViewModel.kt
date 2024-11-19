package com.example.doctorapp.modulePatient.presentation.auth.signUp

import androidx.lifecycle.MutableLiveData
import com.example.doctorapp.domain.core.base.BaseViewModel

class SignUpViewModel : BaseViewModel() {
    private var _validator = MutableLiveData(false)
    val validator: MutableLiveData<Boolean> get() = _validator
    private var _isNameValid = false
    private var _isEmailValid = false
    private var _isPasswordValid = false

    fun setValidState(isNameValid: Boolean? = _isNameValid, isEmailValid: Boolean? = _isEmailValid, isPasswordValid: Boolean? = _isPasswordValid) {
        _isNameValid = isNameValid!!
        _isEmailValid = isEmailValid!!
        _isPasswordValid = isPasswordValid!!
        _validator.value = (_isNameValid &&_isEmailValid && _isPasswordValid)
    }
}