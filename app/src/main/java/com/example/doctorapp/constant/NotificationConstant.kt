package com.example.doctorapp.constant

object NotificationConstant {
    object Status {
        const val READ = "READ"
        const val OPEN = "OPEN"
    }

    object Type {
        const val BOOK_APPOINTMENT_SUCCESS = "BOOK_APPOINTMENT_SUCCESS"
        const val CANCEL_APPOINTMENT_SUCCESS = "CANCEL_APPOINTMENT_SUCCESS"
        const val APPOINTMENT_REMINDER = "APPOINTMENT_REMINDER"
        const val WORKING_SHIFT_REMINDER = "WORKING_SHIFT_REMINDER"
        const val MESSAGE_TEXT  = "TEXT"
        const val MESSAGE_IMAGE = "IMAGE"
        const val MESSAGE_VIDEO = "VIDEO"
    }
}