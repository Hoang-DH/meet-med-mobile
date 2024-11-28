package com.example.doctorapp.network

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.model.User
import retrofit2.http.GET

private const val PATH_USER = "/api/user"

interface UserApiService {

    @GET("$PATH_USER/user-info")
    suspend fun getUserInfo(): ApiResponse<User>

}