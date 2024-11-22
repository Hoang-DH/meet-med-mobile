package com.example.doctorapp.di


import android.content.Context
import android.util.Log
import com.example.doctorapp.moduleDoctor.network.DoctorApiService
import com.example.doctorapp.modulePatient.network.PatientApiService
import com.example.doctorapp.constant.Define
import com.example.doctorapp.utils.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("okhttp", message)
    }.setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).addInterceptor(Interceptor {
                val originalRequest = it.request()
                val prefs = Prefs.getInstance(context)
                val requestBuilder = originalRequest.newBuilder().apply {
                    val authToken = prefs.accessToken
                    addHeader("Authorization", "Bearer $authToken")
                }
                val modifiedRequest = requestBuilder.build()
                val response = it.proceed(modifiedRequest)
                response.newBuilder().code(200).body((response.body?.string() ?: "").toResponseBody(response.body?.contentType())).build()
            }).addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun provideDoctorApiService(okHttpClient: OkHttpClient): DoctorApiService {
        return Retrofit.Builder().baseUrl(Define.Network.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(DoctorApiService::class.java)
    }
    @Provides
    @Singleton
    fun providePatientApiService(okHttpClient: OkHttpClient): PatientApiService {
        return Retrofit.Builder().baseUrl(Define.Network.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PatientApiService::class.java)
    }
}