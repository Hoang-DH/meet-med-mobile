package com.example.doctorapp.modulePatient.presentation.homeContainer.profile

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cloudinary.android.MediaManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentProfileBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.Prefs
import com.example.doctorapp.utils.Utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: ProfileViewModel by viewModels()
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
            tvEditProfile.setOnClickListener {
                appNavigation.openProfileToEditProfile()
            }
            tvNotification.setOnClickListener {
                appNavigation.openProfileToNotificationScreen()
            }
            tvFavorite.setOnClickListener {
                appNavigation.openProfileToFavoriteScreen()
            }
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
                    Prefs.getInstance(requireContext()).isUserLogin = false
                    appNavigation.openHomeContainerToSignIn()
                    showSnackBar("Logout success", binding.root)
                }
            })
    }

    companion object {
        private const val TAG = "ProfileFragment"

        fun newInstance() = ProfileFragment()
    }

}