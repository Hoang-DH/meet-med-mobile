package com.example.doctorapp.modulePatient.presentation.homeContainer.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.MessageRoom
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    private var _messageRoomList: MutableLiveData<MyResponse<List<MessageRoom>>> = MutableLiveData()
    val messageRoomList: MutableLiveData<MyResponse<List<MessageRoom>>> get() = _messageRoomList
    
    fun getMessageRoomList(){
        _messageRoomList.value = MyResponse.Loading
        viewModelScope.launch {
            userRepository.getListChatBox().let { response ->
                if(response.success == true){
                    _messageRoomList.value = MyResponse.Success(response.data?.content ?: emptyList())
                }
                else{
                    when (response.statusCode) {
                        Define.HttpResponseCode.NOT_FOUND -> {
                            _messageRoomList.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.NOT_FOUND
                            )
                        }
                        Define.HttpResponseCode.BAD_REQUEST -> {
                            _messageRoomList.value = MyResponse.Error(
                                Exception(response.message ?: "Error occurred, please try again!"),
                                Define.HttpResponseCode.BAD_REQUEST
                            )
                        }
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _messageRoomList.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.UNAUTHORIZED
                            )
                        }
                        else -> {
                            _messageRoomList.value = MyResponse.Error(
                                Exception("Error occurred, please try again!"),
                                Define.HttpResponseCode.INTERNAL_SERVER_ERROR
                            )
                        }
                    }
                }
            }
        }
    }

}