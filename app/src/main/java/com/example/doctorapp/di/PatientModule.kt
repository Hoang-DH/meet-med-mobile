package com.example.doctorapp.di

import com.example.doctorapp.data.repository.PatientRepositoryImpl
import com.example.doctorapp.domain.repository.PatientRepository
import com.example.doctorapp.modulePatient.network.PatientApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PatientModule {

    @ViewModelScoped
    @Provides
    fun providePatientRepository(patientApi: PatientApiService): PatientRepository {
        return PatientRepositoryImpl(patientApi)
    }

}