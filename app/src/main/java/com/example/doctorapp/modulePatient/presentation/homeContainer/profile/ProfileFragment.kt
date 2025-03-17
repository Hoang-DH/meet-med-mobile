package com.example.doctorapp.modulePatient.presentation.homeContainer.profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.provider.WebAuthProvider.logout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentProfileBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
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

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            context?.let {
                val imageUrl = Prefs.getInstance(requireContext()).patient?.user?.imageUrl
                    progressBar.visibility = View.VISIBLE
                Glide.with(it)
                    .load(if (imageUrl.isNullOrEmpty()) R.drawable.ic_profile_pic else imageUrl)
                    .listener(object : com.bumptech.glide.request.RequestListener<Drawable> {
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .placeholder(R.drawable.ic_profile_pic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivAvatar)
            }
        }
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
//            tvFavorite.setOnClickListener {
//                appNavigation.openProfileToFavoriteScreen()
//            }


        }

    }


    private fun logout() {
        logout(account).withScheme(getString(R.string.com_auth0_scheme))
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