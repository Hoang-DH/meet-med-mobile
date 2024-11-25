package com.example.doctorapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.doctorapp.constant.UserRole
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class Prefs @Inject constructor(context: Context, preFileName: String) {
    private val mPrefs: SharedPreferences by lazy { context.getSharedPreferences(preFileName, Context.MODE_PRIVATE) }

    var isUserLogin: Boolean
        get() = mPrefs.getBoolean(PREFS_KEY_IS_USER_LOGIN, false)
        set(value) = mPrefs.edit().putBoolean(PREFS_KEY_IS_USER_LOGIN, value).apply()

    var accessToken: String
        get() = mPrefs.getString(PREFS_KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = mPrefs.edit().putString(PREFS_KEY_ACCESS_TOKEN, value).apply()

    var userRole: UserRole?
        get() {
            val userRole = mPrefs.getString(PREFS_KEY_USER_ROLE, UserRole.PATIENT.name) ?: ""
            return UserRole.valueOf(userRole)
        }
        set(value) = mPrefs.edit().putString(PREFS_KEY_USER_ROLE, value?.name).apply()

    //save doctor object to shared prefs
    var doctor: Doctor?
        get() {
            val doctorJson = mPrefs.getString(PREFS_KEY_DOCTOR, "")
            return if (doctorJson.isNullOrEmpty()) {
                null
            } else {
                Gson().fromJson(doctorJson, Doctor::class.java)
            }
        }
        set(value) {
            mPrefs.edit().putString(PREFS_KEY_DOCTOR, Gson().toJson(value)).apply()
        }

    //save patient object to shared prefs
    var patient: Patient?
        get() {
            val patientJson = mPrefs.getString(PREFS_KEY_PATIENT, "")
            return if (patientJson.isNullOrEmpty()) {
                null
            } else {
                Gson().fromJson(patientJson, Patient::class.java)
            }
        }
        set(value) {
            mPrefs.edit().putString(PREFS_KEY_PATIENT, Gson().toJson(value)).apply()
        }

    var user: User?
        get() {
            val userJson = mPrefs.getString(PREFS_KEY_USER, "")
            return if (userJson.isNullOrEmpty()) {
                null
            } else {
                Gson().fromJson(userJson, User::class.java)
            }
        }
        set(value) {
            mPrefs.edit().putString(PREFS_KEY_USER, Gson().toJson(value)).apply()
        }

    var isProfileExist: Boolean
        get() = mPrefs.getBoolean(PREFS_KEY_IS_PROFILE_EXIST, false)
        set(value) = mPrefs.edit().putBoolean(PREFS_KEY_IS_PROFILE_EXIST, value).apply()


    companion object{
        private const val SHARE_PREFS_FILE_NAME = "prefs"
        private const val PREFS_KEY_IS_USER_LOGIN = "isUserLogin"
        private const val PREFS_KEY_ACCESS_TOKEN = "accessToken"
        private const val PREFS_KEY_USER_ROLE = "userRole"
        private const val PREFS_KEY_DOCTOR = "doctor"
        private const val PREFS_KEY_PATIENT = "patient"
        private const val PREFS_KEY_USER = "user"
        private const val PREFS_KEY_IS_PROFILE_EXIST = "isProfileExist"
        private var mInstance: Prefs? = null

        fun getInstance(context: Context): Prefs {
            if (mInstance == null) {
                mInstance = Prefs(context, SHARE_PREFS_FILE_NAME)
            }
            return mInstance!!
        }

    }

}