package com.example.doctorapp.modulePatient.presentation.navigation

import android.os.Bundle
import com.example.doctorapp.domain.core.navigation.BaseNavigator

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
    fun openHomeContainerToSignIn(bundle: Bundle? = null)
    fun openProfileToEditProfile(bundle: Bundle? = null)
    fun openProfileToNotificationScreen(bundle: Bundle? = null)
    fun openProfileToFavoriteScreen(bundle: Bundle? = null)
    fun openFavoriteToDoctorDetailScreen(bundle: Bundle? = null)
    fun openSignInToEditProfileScreen(bundle: Bundle? = null)
    fun openEditProfileToHomeContainerScreen(bundle: Bundle? = null)
    fun openBookingAppointmentToMyBookingScreen(bundle: Bundle? = null)
    fun openSignInToDoctorHomeContainerScreen(bundle: Bundle? = null)
    fun openDoctorHomeContainerToSignInScreen(bundle: Bundle? = null)
}