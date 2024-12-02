package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.profile.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Notification
import com.example.doctorapp.data.model.NotificationData
import com.example.doctorapp.data.model.NotificationTimeStamp
import com.example.doctorapp.domain.core.base.BaseViewModel
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorNotificationViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {
    private var _notificationResponse: MutableLiveData<MyResponse<List<Notification>>> = MutableLiveData()
    val notificationResponse: LiveData<MyResponse<List<Notification>>>
        get() = _notificationResponse

    private var _isMarkAsReadSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isMarkAsReadSuccess: LiveData<Boolean>
        get() = _isMarkAsReadSuccess

    private var totalPage: Int = 0
    private val notificationList: ArrayList<NotificationData> = ArrayList()

    fun getListNotification(params: Map<String, Any>){
        if(params[Define.Fields.PAGE] == "0") {
            notificationList.clear()
        }
        _notificationResponse.value = MyResponse.Loading
        viewModelScope.launch {
            userRepository.getUserNotifications(params).let { response ->
                if(response.success == true){
                    notificationList.addAll(response.data?.content ?: emptyList())
                    totalPage = response.data?.totalElements?.div(Define.PAGE_SIZE) ?: 0
                    val processedNotificationList = filterNotiTimestamp(notificationList)
                    _notificationResponse.value = MyResponse.Success(processedNotificationList)
                } else {
                    when(response.statusCode){
                        Define.HttpResponseCode.UNAUTHORIZED -> {
                            _notificationResponse.value = MyResponse.Error(Exception("Unauthorized"), Define.HttpResponseCode.UNAUTHORIZED)
                        }
                        else -> {
                            _notificationResponse.value = MyResponse.Error(Exception("Error"), Define.HttpResponseCode.UNKNOWN)
                        }
                    }
                }

            }
        }
    }

    fun markNotificationAsRead(notificationId: String){
        viewModelScope.launch {
            userRepository.markNotificationAsRead(notificationId).let { response ->
                _isMarkAsReadSuccess.value = response.success == true
            }
        }
    }

    private fun filterNotiTimestamp(notiDataList: ArrayList<NotificationData>): List<Notification> {
        val notificationList = ArrayList<Notification>(notiDataList.sortedByDescending { it.createdAt })
        val sortedNotiDataList = notiDataList.sortedByDescending { it.createdAt }
        var timeStampTitle: String

        for (i in sortedNotiDataList.size - 1 downTo 0) {
            val current = sortedNotiDataList[i]
            val previous = if (i > 0) sortedNotiDataList[i - 1] else null

            when {
                DateUtils.isToday(current.createdAt) -> {
                    if (previous == null || !DateUtils.isToday(previous.createdAt)) {
                        notificationList.add(i, NotificationTimeStamp("Today"))
                    }
                }

                DateUtils.isYesterday(current.createdAt) -> {
                    if (previous == null || !DateUtils.isYesterday(previous.createdAt)) {
                        notificationList.add(i, NotificationTimeStamp("Yesterday"))
                    }
                }

                else -> {
                    timeStampTitle = DateUtils.convertInstantToDate(current.createdAt, "EEEE, MMMM d, yyyy")
                    if (previous == null || !DateUtils.checkDateIsSameDay(current.createdAt, previous.createdAt)) {
                        notificationList.add(i, NotificationTimeStamp(timeStampTitle))
                    }
                }
            }

        }
        return notificationList
    }


}