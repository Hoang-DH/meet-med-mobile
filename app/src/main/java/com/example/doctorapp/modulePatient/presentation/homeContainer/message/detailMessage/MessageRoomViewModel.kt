package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import androidx.lifecycle.MutableLiveData
import com.example.doctorapp.constant.MessageStatus
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.data.model.User
import com.example.doctorapp.domain.core.base.BaseViewModel

class MessageRoomViewModel : BaseViewModel() {
    // TODO: Implement the ViewModel
    private val messages = mutableListOf(
        Message(
            id = "1",
            messageContent = "Hello!",
            patient = Patient(id = "1", user =  User(id = "1", fullName = "Dr. Emily Walker")),
            updatedAt = "2024-10-01 08:00",
            type = "TEXT",
            status = MessageStatus.SENT,
        ),
        Message(
            id = "2",
            messageContent = "How are you?",
            doctor = Doctor(id = "2", user = User(id = "2", fullName = "Dr. John Smith")),
            updatedAt = "2024-10-01 08:05",
            type = "TEXT",
            status = MessageStatus.SENDING,
        ),
//        Message(
//            id = "3",
//            messageContent = "Good morning",
//            senderId = "1",
//            sendDate = "2024-10-01 08:10",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "4",
//            messageContent = "See you soon",
//            senderId = "2",
//            sendDate = "2024-10-01 08:15",
//            type = "IMAGE",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "5",
//            messageContent = "Take care",
//            senderId = "1",
//            sendDate = "2024-10-01 08:20",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "6",
//            messageContent = "Thank you",
//            senderId = "2",
//            sendDate = "2024-10-01 08:25",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "7",
//            messageContent = "Goodbye",
//            senderId = "1",
//            sendDate = "2024-10-01 08:30",
//            type = "VIDEO",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "8",
//            messageContent = "Yes, sure",
//            senderId = "2",
//            sendDate = "2024-10-01 08:35",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "9",
//            messageContent = "No problem",
//            senderId = "1",
//            sendDate = "2024-10-01 08:40",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "10",
//            messageContent = "See you later",
//            senderId = "2",
//            sendDate = "2024-10-01 08:45",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "11",
//            messageContent = "What's up?",
//            senderId = "1",
//            sendDate = "2024-10-01 08:50",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "12",
//            messageContent = "I'm fine",
//            senderId = "2",
//            sendDate = "2024-10-01 08:55",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "13",
//            messageContent = "Let's meet",
//            senderId = "1",
//            sendDate = "2024-10-01 09:00",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "14",
//            messageContent = "Sure",
//            senderId = "2",
//            sendDate = "2024-10-01 09:05",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "15",
//            messageContent = "Where?",
//            senderId = "1",
//            sendDate = "2024-10-01 09:10",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "16",
//            messageContent = "At the park",
//            senderId = "2",
//            sendDate = "2024-10-01 09:15",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "17",
//            messageContent = "Okay",
//            senderId = "1",
//            sendDate = "2024-10-01 09:20",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "18",
//            messageContent = "See you there",
//            senderId = "2",
//            sendDate = "2024-10-01 09:25",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        ),
//        Message(
//            id = "19",
//            messageContent = "Bye",
//            senderId = "1",
//            sendDate = "2024-10-01 09:30",
//            type = "TEXT",
//            status = MessageStatus.SENT,
//            senderProfile = User(id = "1", fullName = "Dr. Emily Walker")
//        ),
//        Message(
//            id = "20",
//            messageContent = "Bye",
//            senderId = "2",
//            sendDate = "2024-10-01 09:35",
//            type = "TEXT",
//            status = MessageStatus.SENDING,
//            senderProfile = User(id = "2", fullName = "Dr. John Smith")
//        )
    )



    private var _messageList: MutableLiveData<List<Message>> = MutableLiveData(messages)
    val messageList: MutableLiveData<List<Message>>
        get() = _messageList

    fun sendMessage(message: Message) {
        messages.add((messages.size).coerceAtLeast(0), message)
        _messageList.value = messages
    }

    fun updateMessageStatus(message: Message, status: MessageStatus) {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1) {
            messages[index].status = status
            _messageList.value = messages
        }
    }
}