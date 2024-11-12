package com.example.doctorapp.di

import androidx.lifecycle.ViewModel
import com.example.doctorapp.data.repository.DoctorRepositoryImpl
import com.example.doctorapp.domain.repository.DoctorRepository
import com.example.doctorapp.network.DoctorApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DoctorModule {

    @ViewModelScoped
    @Provides
    fun provideDoctorRepository(doctorApi: DoctorApiService): DoctorRepository {
        return DoctorRepositoryImpl(doctorApi)
    }
}