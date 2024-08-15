package com.example.doctorapp.presentation.auth.signUp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.domain.core.base.BaseFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSignUpBinding
import com.example.doctorapp.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Spanner
import com.example.doctorapp.utils.validate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>(R.layout.fragment_sign_up) {

    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: SignUpViewModel by viewModels()
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Spanner.spanString(
            binding.tvSignIn,
            resources.getString(R.string.string_sign_in),
            resources
        ) {
            appNavigation.openSignUpToSignInScreen()
        }
    }

//    override fun bindingStateView() {
//        super.bindingStateView()
//        binding.apply {
//
//            etName.validate { email ->
//                if (email.isEmpty()) {
//                    etName.error =
//                        resources.getString(R.string.do_not_leave_this_field_blank)
//                } else {
//                    viewModel.setValidState(isNameValid = true)
//                }
//            }
//
//            etEmail.validate { email ->
//                if (email.isEmpty()) {
//                    etEmail.error =
//                        resources.getString(R.string.do_not_leave_this_field_blank)
//                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    etEmail.error = resources.getString(R.string.invalid_email)
//                } else {
//                    viewModel.setValidState(isEmailValid = true)
//                }
//            }
//            etPassword.validate { password ->
//                if (password.isEmpty()) {
//                    etPassword.error =
//                        resources.getString(R.string.do_not_leave_this_field_blank)
//                } else if (password.length < 6) {
//                    etPassword.error = resources.getString(R.string.invalid_password)
//                } else {
//                    viewModel.setValidState(isPasswordValid = true)
//                }
//            }
//        }
//        viewModel.validator.observe(viewLifecycleOwner) { isValid ->
//            binding.btnSignUp.isEnabled = isValid
//        }
//    }

    override fun setOnClick() {
        super.setOnClick()
        binding.btnSignUp.isEnabled = true
        binding.btnSignUp.setOnClickListener {
            appNavigation.openSignUpToSignUpProfileScreen()
        }
    }

}