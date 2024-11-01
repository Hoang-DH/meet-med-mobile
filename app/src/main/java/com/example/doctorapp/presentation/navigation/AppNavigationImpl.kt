package com.example.doctorapp.presentation.navigation

import android.os.Bundle
import com.example.doctorapp.domain.core.navigation.BaseNavigatorImpl
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

    override fun openHomeContainerToSignIn(bundle: Bundle?) {
        openScreen(R.id.action_homeContainerFragment_to_signInFragment)
    }

    override fun openProfileToEditProfile(bundle: Bundle?) {
        openScreen(R.id.action_profileFragment_to_editProfileFragment, bundle)
    }

    override fun openProfileToNotificationScreen(bundle: Bundle?) {
        openScreen(R.id.action_profileFragment_to_notificationFragment, bundle)
    }

    override fun openProfileToFavoriteScreen(bundle: Bundle?) {
        openScreen(R.id.action_profileFragment_to_favoriteFragment, bundle)
    }

    override fun openFavoriteToDoctorDetailScreen(bundle: Bundle?) {
        openScreen(R.id.action_favoriteFragment_to_doctorDetailFragment, bundle)
    }

}