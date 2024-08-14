package com.example.doctorapp.presentation.auth.signUp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.domain.core.base.BaseFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSignUpProfileBinding

class SignUpProfileFragment : BaseFragment<FragmentSignUpProfileBinding, SignUpProfileViewModel>(R.layout.fragment_sign_up_profile) {

    companion object {
        fun newInstance() = SignUpProfileFragment()
    }

    private val viewModel: SignUpProfileViewModel by viewModels()
    override fun getVM() = viewModel


}