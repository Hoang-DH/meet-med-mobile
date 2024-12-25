package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.constant.MessageStatus
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.data.model.User
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MessageRoomViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    // TODO: Implement the ViewModel
    private val messages = mutableListOf<Message>()

    private var _messageList: MutableLiveData<MyResponse<List<Message>>> = MutableLiveData()
    val messageList: MutableLiveData<MyResponse<List<Message>>>
        get() = _messageList
    
    fun getMessagesOfChatBox(chatBoxId: String, params: Map<String, Any>) {
        if(params[Define.Fields.PAGE] == "0") {
            messages.clear()
        }
        _messageList.value = MyResponse.Loading
        viewModelScope.launch {
            userRepository.getMessagesOfChatBox(chatBoxId, params).let { response ->
                if(response.success == true){
                    messages.addAll(response.data?.content ?: emptyList())
                    _messageList.value = MyResponse.Success(messages)
                }
                else{
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
        _messageList.value = MyResponse.Success(messages)
    }

    fun updateMessageStatus(message: Message, status: MessageStatus) {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1) {
            messages[index].status = status
            _messageList.value = MyResponse.Success(messages)
            Log.d("TAG", "updateMessageStatus: i")
        }
    }
}