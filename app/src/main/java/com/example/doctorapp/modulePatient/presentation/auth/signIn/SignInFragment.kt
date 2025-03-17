package com.example.doctorapp.modulePatient.presentation.auth.signIn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.constant.UserRole
import com.example.doctorapp.databinding.FragmentSignInBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import com.example.doctorapp.utils.Utils.showSnackBar
import com.google.firebase.messaging.FirebaseMessaging
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

    override fun initView(savedInstanceState: Bundle?) {
        if (Prefs.getInstance(requireContext()).isUserLogin) {
            if (Prefs.getInstance(requireContext()).userRole == UserRole.DOCTOR) {
                appNavigation.openSignInToDoctorHomeContainerScreen()
//                logout()
            } else {
                appNavigation.openSignInToHomeContainerScreen()
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
                    Prefs.getInstance(requireContext()).apply {
                        isUserLogin = false
                        patient = null
                        doctor = null
                        user = null
                        isProfileExist = false
                        accessToken = ""
                        userRole = null
                    }
                   showSnackBar("Logout success", binding.root)
                }
            })
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.patientProfileResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    Prefs.getInstance(requireContext()).patient = response.data
                    Prefs.getInstance(requireContext()).isProfileExist = true
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(requireActivity()) { task ->
                        if (!task.isSuccessful) {
                            Log.w("HoangDH", "Fetching FCM registration token failed", task.exception)
                            return@addOnCompleteListener
                        }
                        val token = task.result
                        Prefs.getInstance(requireContext()).deviceToken = token
                        Log.d("HoangDH", "deviceToken: ${Prefs.getInstance(requireContext()).accessToken}")
                    }
                    appNavigation.openSignInToHomeContainerScreen()
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    if (response.statusCode == Define.HttpResponseCode.BAD_REQUEST
                        && response.exception.message == Define.HttpResponseMessage.PATIENT_PROFILE_NOT_FOUND
                    ) {
                        Prefs.getInstance(requireContext()).isProfileExist = false
                        appNavigation.openSignInToEditProfileScreen()
                    } else{
                        Dialog.showDialogError(requireContext(), response.exception.message.toString())
                    }
                }

            }
        }

        viewModel.doctorProfileResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    Prefs.getInstance(requireContext()).doctor = response.data
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(requireActivity()) { task ->
                        if (!task.isSuccessful) {
                            Log.w("HoangDH", "Fetching FCM registration token failed", task.exception)
                            return@addOnCompleteListener
                        }
                        val token = task.result
                        Prefs.getInstance(requireContext()).deviceToken = token
                        Log.d("HoangDH", "deviceToken: $token")
                    }
                    appNavigation.openSignInToDoctorHomeContainerScreen()
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    appNavigation.openSignInToDoctorHomeContainerScreen()
                }
            }
        }

        viewModel.userInfoResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    Prefs.getInstance(requireContext()).user = response.data
                    if (response.data.role == "HeadDoctor" || response.data.role == "Doctor") {
                        Prefs.getInstance(requireContext()).userRole = UserRole.DOCTOR
                        viewModel.getDoctorProfile()
                    } else {
                        Prefs.getInstance(requireContext()).userRole = UserRole.PATIENT
                        viewModel.getPatientProfile()
                    }
                }
                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }

            }
        }
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
                }

                override fun onSuccess(result: Credentials) {
                    Prefs.getInstance(requireContext()).apply {
                        accessToken = result.accessToken
                        isUserLogin = true
                    }
                    Log.d("HoangDH", "accessToken: ${result.accessToken}")
//                    Log.d("HoangDH", "userRole: ${result.getExtraInfo()["system_role"]}")
                    viewModel.getUserInfo()
                }
            })
    }

//    private fun showUserProfile(accessToken: String) {
//        val client = AuthenticationAPIClient(account)
//
//        // With the access token, call `userInfo` and get the profile from Auth0.
//        client.userInfo(accessToken)
//            .start(object : Callback<UserProfile, AuthenticationException> {
//                override fun onFailure(error: AuthenticationException) {
//                    // Something went wrong!
//                }
//
//                override fun onSuccess(result: UserProfile) {
//                    // We have the user's profile!
//                    Prefs.getInstance(requireContext()).user = Gson().fromJson(
//                        Gson().toJson(result.getExtraInfo()["user_info"]),
//                        User::class.java
//                    )
//                    if (result.getExtraInfo()["system_role"] == "Head Doctor") {
//                        Prefs.getInstance(requireContext()).userRole = UserRole.DOCTOR
////                        viewModel.getDoctorProfile()
//                        appNavigation.openSignInToDoctorHomeContainerScreen()
//                    } else {
//                        Prefs.getInstance(requireContext()).userRole = UserRole.PATIENT
//                        viewModel.getPatientProfile()
//                    }
//
//                    Log.d("HoangDH", "accessToken: $accessToken")
//                    Log.d("HoangDH", "userRole: ${result.getExtraInfo()["system_role"]}")
//                }
//            })
//    }


}