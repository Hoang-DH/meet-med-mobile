package com.example.doctorapp.di

import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun provideAppNavigation(navigation: AppNavigationImpl): AppNavigation

}