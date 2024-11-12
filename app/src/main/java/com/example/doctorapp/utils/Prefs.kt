package com.example.doctorapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.doctorapp.constant.UserRole
import com.example.doctorapp.data.model.Doctor
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

    var userRole: UserRole
        get() {
            val userRole = mPrefs.getString(PREFS_KEY_USER_ROLE, UserRole.PATIENT.name) ?: ""
            return UserRole.valueOf(userRole)
        }
        set(value) = mPrefs.edit().putString(PREFS_KEY_USER_ROLE, value.name).apply()

    companion object{
        private const val SHARE_PREFS_FILE_NAME = "prefs"
        private const val PREFS_KEY_IS_USER_LOGIN = "isUserLogin"
        private const val PREFS_KEY_ACCESS_TOKEN = "accessToken"
        private const val PREFS_KEY_USER_ROLE = "userRole"
        private var mInstance: Prefs? = null

        fun getInstance(context: Context): Prefs {
            if (mInstance == null) {
                mInstance = Prefs(context, SHARE_PREFS_FILE_NAME)
            }
            return mInstance!!
        }

    }

}