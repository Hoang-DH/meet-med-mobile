package com.example.doctorapp.network

import com.example.doctorapp.data.dto.ApiResponse
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.dto.PagingResponse
import com.example.doctorapp.domain.model.Message
import com.example.doctorapp.domain.model.MessageRoom
import com.example.doctorapp.domain.model.NotificationData
import com.example.doctorapp.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

private const val PATH_USER = "/api/user"
private const val PATH_FCM = "/api/fcm-device-token"
private const val PATH_NOTIFICATION = "/api/notification"
private const val PATH_CHAT_BOX = "/api/chat-box"

interface UserApiService {

    @GET("$PATH_USER/user-info")
    suspend fun getUserInfo(): ApiResponse<User>

    @POST(PATH_FCM)
    suspend fun postFCMDeviceToken(@Body fcm: Fcm): ApiResponse<Unit>

    @GET(PATH_NOTIFICATION)
    @JvmSuppressWildcards
    suspend fun getUserNotifications(@QueryMap params: Map<String, Any>): ApiResponse<PagingResponse<NotificationData>>

    @PUT("$PATH_NOTIFICATION/{notificationId}/read")
    suspend fun markNotificationAsRead(@Path("notificationId") notificationId: String): ApiResponse<Unit>

    @GET(PATH_CHAT_BOX)
    suspend fun getListChatBox(): ApiResponse<PagingResponse<MessageRoom>>

    @GET("$PATH_CHAT_BOX/{chatBoxId}/messages")
    @JvmSuppressWildcards
    suspend fun getMessagesOfChatBox(@Path("chatBoxId") chatBoxId: String, @QueryMap params: Map<String, Any>): ApiResponse<PagingResponse<Message>>

}