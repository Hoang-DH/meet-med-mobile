package com.example.doctorapp.network

import android.content.Context
import com.example.doctorapp.utils.Prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor (private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val prefs = Prefs.getInstance(context)
        val requestBuilder: Request.Builder = originalRequest.newBuilder().apply {
            val authToken = prefs.accessToken
            addHeader("Authorization", "Bearer $authToken")
        }
        val modifiedRequest = requestBuilder.build()
        return chain.proceed(modifiedRequest)
    }
}