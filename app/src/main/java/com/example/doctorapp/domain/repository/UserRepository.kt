package com.example.doctorapp.domain.repository

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.data.model.NotificationData
import com.example.doctorapp.data.model.User

interface UserRepository {
    suspend fun getUserInfo(): ApiResponse<User>
    suspend fun postFCMDeviceToken(fcm: Fcm): ApiResponse<Unit>
    suspend fun getUserNotifications(params: Map<String, Any>): ApiResponse<PagingResponse<NotificationData>>
    suspend fun markNotificationAsRead(notificationId: String): ApiResponse<Unit>
    suspend fun getListChatBox(): ApiResponse<PagingResponse<MessageRoom>>
    suspend fun getMessagesOfChatBox(chatBoxId: String, params: Map<String, Any>): ApiResponse<PagingResponse<Message>>
}