package com.example.doctorapp.presentation.container.roleDoctor

import androidx.activity.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.ActivityMainDoctorBinding
import com.example.doctorapp.domain.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDoctorActivity : BaseActivity<ActivityMainDoctorBinding, MainDoctorActivityViewModel>() {

    private val viewModel: MainDoctorActivityViewModel by viewModels()
    override fun getVM() = viewModel

    override val layoutId: Int
        get() = R.layout.activity_main_doctor



}