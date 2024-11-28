package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.profile

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDoctorProfileBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Prefs
import com.example.doctorapp.utils.Utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorProfileFragment : BaseFragment<FragmentDoctorProfileBinding, DoctorProfileViewModel>(R.layout.fragment_doctor_profile) {

    companion object {
        fun newInstance() = DoctorProfileFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: DoctorProfileViewModel by viewModels()
    override fun getVM() = viewModel
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = Auth0(
            resources.getString(R.string.com_auth0_client_id),
            resources.getString(R.string.com_auth0_domain)
        )
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {

            tvLogout.setOnClickListener {
                logout()
            }
//            tvEditProfile.setOnClickListener {
//                appNavigation.openProfileToEditProfile()
//            }
//            tvNotification.setOnClickListener {
//                appNavigation.openProfileToNotificationScreen()
//            }
//            tvFavorite.setOnClickListener {
//                appNavigation.openProfileToFavoriteScreen()
//            }
        }


    }

    private fun logout() {
        WebAuthProvider.logout(account).withScheme(getString(R.string.com_auth0_scheme))
            .start(requireContext(), object :
                Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    showSnackBar("Error: ${error.message}", binding.root)
                }

                override fun onSuccess(result: Void?) {
                    Prefs.getInstance(requireContext()).apply {
                        isUserLogin = false
                        patient = null
                        doctor = null
                        user = null
                        isProfileExist = false
                        accessToken = ""
                        userRole = null
                    }
                    appNavigation.openDoctorHomeContainerToSignInScreen()
                    showSnackBar("Logout success", binding.root)
                }
            })
    }
}