package com.example.doctorapp.presentation.navigation

import android.os.Bundle
import com.example.chatapp.domain.core.navigation.BaseNavigator

interface AppNavigation: BaseNavigator {
    fun openSplashToSignInScreen(bundle: Bundle? = null)
    fun openSignInToSignUpScreen(bundle: Bundle? = null)
    fun openSignUpToSignInScreen(bundle: Bundle? = null)
    fun openSignInToHomeContainerScreen(bundle: Bundle? = null)
    fun openHomeContainerToSearchDoctorScreen(bundle: Bundle? = null)
    fun openSearchDoctorToHomeContainerScreen(bundle: Bundle? = null)
    fun openSearchDoctorToDoctorDetailScreen(bundle: Bundle? = null)
    fun openDoctorDetailToSearchDoctorScreen(bundle: Bundle? = null)
    fun openDoctorDetailToBookingAppointmentScreen(bundle: Bundle? = null)
}