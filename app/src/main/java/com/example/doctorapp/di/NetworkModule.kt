package com.example.doctorapp.di


import android.content.Context
import com.example.doctorapp.network.AuthInterceptor
import com.example.doctorapp.network.DoctorApiService
import com.example.doctorapp.utils.Define
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).addInterceptor(AuthInterceptor(context)).build()

    @Provides
    @Singleton
    fun provideApiService(context: Context): DoctorApiService {
        return Retrofit.Builder().baseUrl(Define.Network.BASE_URL).client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create()).build().create(DoctorApiService::class.java)
    }
}