package com.example.doctorapp.utils

sealed class MyResponse<out T> {
    data class Success<out T>(val data: T): MyResponse<T>()
    data class Error(val exception: Exception): MyResponse<Nothing>()
    data object Loading: MyResponse<Nothing>()
}