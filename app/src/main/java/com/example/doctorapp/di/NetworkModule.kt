package com.example.doctorapp.di


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
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideApiService(): DoctorApiService {
        return Retrofit.Builder().baseUrl(Define.Network.BASE_URL).client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build().create(DoctorApiService::class.java)
    }
}