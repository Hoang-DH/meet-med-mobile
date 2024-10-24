package com.example.doctorapp.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.example.chatapp.domain.core.navigation.BaseNavigator
import com.example.chatapp.domain.core.navigation.BaseNavigatorImpl
import com.example.doctorapp.R
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigationImpl @Inject constructor() : AppNavigation, BaseNavigatorImpl() {
    override fun openSplashToSignInScreen(bundle: Bundle?) {
        openScreen(R.id.action_splashFragment_to_signInFragment)
    }

    override fun openSignInToSignUpScreen(bundle: Bundle?) {
        openScreen(R.id.action_signInFragment_to_signUpFragment)
    }

    override fun openSignUpToSignInScreen(bundle: Bundle?) {
        openScreen(R.id.action_signUpFragment_to_signInFragment)
    }


    override fun openSignInToHomeContainerScreen(bundle: Bundle?) {
        openScreen(R.id.action_signInFragment_to_homeContainerFragment)
    }

    override fun openHomeContainerToSearchDoctorScreen(bundle: Bundle?) {
        openScreen(R.id.action_homeContainerFragment_to_searchDoctorFragment)
    }

    override fun openSearchDoctorToHomeContainerScreen(bundle: Bundle?) {
        openScreen(R.id.action_searchDoctorFragment_to_homeContainerFragment)
    }

    override fun openSearchDoctorToDoctorDetailScreen(bundle: Bundle?) {
        openScreen(R.id.action_searchDoctorFragment_to_doctorDetailFragment, bundle)
    }

    override fun openDoctorDetailToSearchDoctorScreen(bundle: Bundle?) {
        openScreen(R.id.action_doctorDetailFragment_to_searchDoctorFragment)
    }

    override fun openDoctorDetailToBookingAppointmentScreen(bundle: Bundle?) {
        openScreen(R.id.action_doctorDetailFragment_to_bookingAppointmentFragment)
    }

}