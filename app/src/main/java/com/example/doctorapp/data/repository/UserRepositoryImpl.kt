package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.model.User
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.network.UserApiService
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(private val userApi: UserApiService): UserRepository {
    override suspend fun getUserInfo(): ApiResponse<User> {
        return userApi.getUserInfo()
    }
}