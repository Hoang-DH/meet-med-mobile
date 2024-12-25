package com.example.doctorapp.constant

object Define {
    const val PAGE_SIZE = 20

    object HttpResponseCode {
        const val UNKNOWN = -1
        const val OK = 200
        const val CREATED = 201
        const val NO_CONTENT = 204
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
    }

    object HttpResponseMessage {
        const val PATIENT_PROFILE_NOT_FOUND = "Patient profile not found"
    }



    object Fields {
        const val DOCTOR_APPOINTMENT = "doctorAppointment"
        const val CATEGORY = "category"
        const val DATA = "data"
        const val META = "meta"
        const val MESSAGE = "message"
        const val STATUS_CODE = "statusCode"
        const val SUCCESS = "success"
        const val DOCTOR_ID = "doctorId"
        const val PAGE = "page"
        const val SIZE = "size"
        const val SEARCH = "search"
        const val ORDER = "order"
        const val ORDER_BY = "orderBy"
        const val DEPARTMENT = "department"
        const val STATUS = "status"
    }

    object BookingStatus {
        const val UPCOMING = "UPCOMING"
        const val COMPLETED = "COMPLETED"
        const val CANCELLED = "cancelled"
    }

    object BundleKey {
        const val DOCTOR_APPOINTMENT_ID = "doctorAppointmentId"
        const val DOCTOR_APPOINTMENT = "doctorAppointment"
        const val APPOINTMENT_ID = "appointmentId"
        const val NOTIFICATION = "notification"
        const val BOOKING_SHIFT = "bookingShift"
        const val DEPARTMENT = "department"
        const val DOCTOR = "doctor"
        const val IS_FROM = "isFrom"
        const val MESSAGE_ROOM = "messageRoom"
    }

    object IntentKey {
        const val NOTIFICATION_TYPE = "notificationType"
    }

    object WorkingTab {
        const val MY_SHIFTS = "my_shifts"
        const val REGISTER_NEW_SHIFT = "register_new_shift"
    }

    object AppointmentTab {
        const val UPCOMING = "UPCOMING"
        const val COMPLETED = "COMPLETED"
    }

    object Network {
        const val BASE_URL = "http://clinic.thanhnguyen03.site"
    }

    object IsFrom {
        const val IS_FROM_HOME_SCREEN = "isFromHomeScreen"
    }

    object Socket{
        const val SOCKET_URL = "http://clinic.thanhnguyen03.site/socket.io"
        const val EVENT_RECEIVE_MESSAGE = "receive-message"
        const val EVENT_ERROR = "error"
        const val EVENT_CONNECTED = "connected"
        const val EVENT_SEND_MESSAGE = "send-message"
        const val EVENT_MESSAGE_ACK = "message-ack"
        const val TO = "to"
        const val MESSAGE = "message"
        const val CONTENT = "content"
        const val TYPE = "type"
        const val CHAT_BOX_ID = "chat_box_id"
    }

    object MessageReadStatus {
        const val READ = 1
        const val UNREAD = 0
    }


}