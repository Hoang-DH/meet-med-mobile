package com.example.doctorapp.data.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.data.model.NotificationData
import com.example.doctorapp.data.model.User
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.network.UserApiService
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(private val userApi: UserApiService): UserRepository {
    override suspend fun getUserInfo(): ApiResponse<User> {
        return userApi.getUserInfo()
    }

    override suspend fun postFCMDeviceToken(fcm: Fcm): ApiResponse<Unit> {
        return userApi.postFCMDeviceToken(fcm)
    }

    override suspend fun getUserNotifications(params: Map<String, Any>): ApiResponse<PagingResponse<NotificationData>> {
        return userApi.getUserNotifications(params)
    }

    override suspend fun markNotificationAsRead(notificationId: String): ApiResponse<Unit> {
        return userApi.markNotificationAsRead(notificationId)
    }

    override suspend fun getListChatBox(): ApiResponse<PagingResponse<MessageRoom>> {
        return userApi.getListChatBox()
    }
}