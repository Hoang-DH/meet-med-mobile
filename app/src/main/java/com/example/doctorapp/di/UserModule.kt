package com.example.doctorapp.di

import com.example.doctorapp.data.repository.UserRepositoryImpl
import com.example.doctorapp.domain.repository.UserRepository
import com.example.doctorapp.network.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UserModule {

    @ViewModelScoped
    @Provides
    fun provideUserRepository(userApi: UserApiService): UserRepository {
        return UserRepositoryImpl(userApi)
    }

}