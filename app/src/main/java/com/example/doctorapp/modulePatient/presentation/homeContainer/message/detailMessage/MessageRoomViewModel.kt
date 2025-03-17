package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.constant.MessageStatus
import com.example.doctorapp.domain.model.Message
import com.example.doctorapp.domain.model.MessageData
import com.example.doctorapp.domain.model.MessageTimeStamp
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject
import kotlin.math.abs


@HiltViewModel
class MessageRoomViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    // TODO: Implement the ViewModel
    private val messages = ArrayList<Message>()

    private var _messageList: MutableLiveData<MyResponse<List<MessageData>>> = MutableLiveData()
    val messageList: MutableLiveData<MyResponse<List<MessageData>>>
        get() = _messageList

    fun getMessagesOfChatBox(chatBoxId: String, params: Map<String, Any>) {
        if (params[Define.Fields.PAGE] == "0") {
            messages.clear()
        }
        _messageList.value = MyResponse.Loading
        viewModelScope.launch {
            userRepository.getMessagesOfChatBox(chatBoxId, params).let { response ->
                if (response.success == true) {
                    messages.addAll(response.data?.content ?: emptyList())
                    _messageList.value = MyResponse.Success(groupMessagesByTimestamp(processThumbnail(messages)))
                } else {
                    when (response.statusCode) {
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _messageList.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.NOT_FOUND
                            )
                        }

                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _messageList.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.BAD_REQUEST
                            )
                        }

                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _messageList.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.UNAUTHORIZED
                            )
                        }

                        else -> {
                            _messageList.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.INTERNAL_SERVER_ERROR
                            )
                        }
                    }
                }
            }
        }
    }

    fun sendMessage(message: Message) {
        messages.add(0.coerceAtLeast(0), message)
        _messageList.value = MyResponse.Success(groupMessagesByTimestamp(messages))
    }

    fun updateMessageStatus(message: Message, status: MessageStatus) {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1) {
            messages[index].status = status
            messages[index].thumbnail = message.thumbnail
            _messageList.value = MyResponse.Success(groupMessagesByTimestamp(messages))
            Log.d("TAG", "updateMessageStatus: i")
        }
    }

    private fun processThumbnail(messages: List<Message>): List<Message> {
        for(message in messages){
            if(message.messageContent != null && message.messageContent!!.endsWith(".mp4", ignoreCase = true)){
                message.thumbnail = message.messageContent!!.replace(".mp4", ".jpg")
            }
        }
        return messages
    }

    private fun groupMessagesByTimestamp(messages: List<Message>): List<MessageData> {
        val groupedMessages = mutableListOf<MessageData>()
        var currentGroup = mutableListOf<Message>()
        var previousMessage: Message? = null

        for (message in messages) {
            if (previousMessage != null) {
                val previousTimestamp = Instant.parse(previousMessage.updatedAt)
                val currentTimestamp = Instant.parse(message.updatedAt)
                val duration = Duration.between(previousTimestamp, currentTimestamp)

                if (abs(duration.toMinutes()) >= 15) {
                    // Add the current group to the result list
                    groupedMessages.addAll(currentGroup)
                    // Add a timestamp item at the end of the group
                    val timestamp = DateUtils.convertInstantToDate(previousMessage.updatedAt, "MMM dd, HH:mm")
                    groupedMessages.add(MessageTimeStamp(timestamp))
                    // Start a new group
                    currentGroup = mutableListOf()
                }
            }
            currentGroup.add(message)
            previousMessage = message
        }

        // Add the last group to the result list
        if (currentGroup.isNotEmpty()) {
            groupedMessages.addAll(currentGroup)
            val timestamp = DateUtils.convertInstantToDate(previousMessage?.updatedAt, "MMM dd, HH:mm")
            groupedMessages.add(MessageTimeStamp(timestamp))
        }

        return groupedMessages
    }
}