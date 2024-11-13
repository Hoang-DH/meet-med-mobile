package com.example.doctorapp.utils

object Define {
    object HttpResponseCode {
        const val OK = 200
        const val CREATED = 201
        const val NO_CONTENT = 204
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val INTERNAL_SERVER_ERROR = 500
    }

    object Fields{
        const val CATEGORY = "category"
        const val DATA = "data"
        const val META = "meta"
        const val MESSAGE = "message"
        const val STATUS_CODE = "status_code"
    }

    object BookingStatus{
        const val UPCOMING = "upcoming"
        const val COMPLETED = "completed"
        const val CANCELLED = "cancelled"
    }

    object BundleKey {
        const val DEPARTMENT = "department"
        const val DOCTOR = "doctor"
    }

    object WorkingTab {
        const val MY_SHIFTS = "my_shifts"
        const val REGISTER_NEW_SHIFT = "register_new_shift"
    }

    object Network {
        const val BASE_URL = "https://6a39-171-224-179-76.ngrok-free.app"
    }

}