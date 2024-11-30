package com.example.doctorapp.network

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val PATH_USER = "/api/user"
private const val PATH_FCM = "/api/fcm-device-token"
interface UserApiService {

    @GET("$PATH_USER/user-info")
    suspend fun getUserInfo(): ApiResponse<User>

    @POST(PATH_FCM)
    suspend fun postFCMDeviceToken(@Body fcm: Fcm): ApiResponse<Unit>

}