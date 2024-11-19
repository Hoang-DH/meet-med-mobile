package com.example.doctorapp.utils

sealed class MyResponse<out T> {
    data class Success<out T>(val data: T): MyResponse<T>()
    data class Error<out T>(val exception: Exception, val statusCode: Int): MyResponse<T>()
    data object Loading: MyResponse<Nothing>()
}