package com.example.doctorapp.constant

object Define {
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

    object DoctorBottomNav{
        const val HOME = 0
        const val APPOINTMENT = 1
        const val WORKING = 2
        const val PROFILE = 3
    }

    object HttpResponseMessage {
        const val PATIENT_PROFILE_NOT_FOUND = "Patient profile not found"
    }

    object Fields {
        const val CATEGORY = "category"
        const val DATA = "data"
        const val META = "meta"
        const val MESSAGE = "message"
        const val STATUS_CODE = "statusCode"
        const val SUCCESS = "success"
        const val DOCTOR_ID = "doctorId"
        const val PAGE = "page"
        const val SIZE = "size"
        const val NAME = "name"
        const val ORDER = "order"
        const val ORDER_BY = "orderBy"
        const val DEPARTMENT = "department"
    }

    object BookingStatus {
        const val UPCOMING = "upcoming"
        const val COMPLETED = "completed"
        const val CANCELLED = "cancelled"
    }

    object BundleKey {
        const val DEPARTMENT = "department"
        const val DOCTOR = "doctor"
        const val IS_FROM = "isFrom"
    }

    object WorkingTab {
        const val MY_SHIFTS = "my_shifts"
        const val REGISTER_NEW_SHIFT = "register_new_shift"
    }

    object AppointmentTab {
        const val UPCOMING = "upcoming"
        const val COMPLETED = "completed"
    }

    object Network {
        const val BASE_URL = "http://clinic.thanhnguyen03.site"
    }

    object IsFrom {
        const val IS_FROM_HOME_SCREEN = "isFromHomeScreen"
    }

}