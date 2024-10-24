package com.example.doctorapp.presentation.auth.signIn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSignInBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.navigation.AppNavigation
import com.example.doctorapp.presentation.utils.Utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {

    @Inject
    lateinit var appNavigation: AppNavigation
    private lateinit var account: Auth0

    private val viewModel: SignInViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = Auth0(
            resources.getString(R.string.com_auth0_client_id),
            resources.getString(R.string.com_auth0_domain)
        )
    }

    override fun bindingAction() {
        super.bindingAction()
        binding.btnSignIn.setOnClickListener {
            loginWithBrowser()
        }
    }

    private fun loginWithBrowser() {
        WebAuthProvider.login(account)
            .withScheme(getString(R.string.com_auth0_scheme))
            .withAudience("http://localhost:8000")

            // Launch the authentication passing the callback where the results will be received
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    showSnackBar("Error: ${error.message}", binding.root)
                }

                override fun onSuccess(result: Credentials) {
//                    cachedCredentials = credentials
                    showSnackBar("Success: ${result.accessToken}", binding.root)
//                    updateUI()
                    val accessToken = result.accessToken
                    showUserProfile(accessToken)
                    appNavigation.openSignInToHomeContainerScreen()
                }
            })
    }

    private fun showUserProfile(accessToken: String) {
        val client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                }

                override fun onSuccess(profile: UserProfile) {
                    // We have the user's profile!
                    val email = profile.email
                    val name = profile.name
                    Log.d("HoangDH", "email: $email")
                    Log.d("HoangDH", "name: $name")
                }
            })
    }


}