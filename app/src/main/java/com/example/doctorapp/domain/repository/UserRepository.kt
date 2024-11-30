package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.model.User

interface UserRepository {
    suspend fun getUserInfo(): ApiResponse<User>
    suspend fun postFCMDeviceToken(fcm: Fcm): ApiResponse<Unit>
}