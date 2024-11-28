package com.example.doctorapp.moduleDoctor.presentation.container

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.ActivityMainDoctorBinding
import com.example.doctorapp.domain.core.base.BaseActivity
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainDoctorActivity : BaseActivity<ActivityMainDoctorBinding, MainDoctorActivityViewModel>() {

    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        const val TAG = "MainDoctorActivity"
        fun newInstance() = MainDoctorActivity()
    }

    private val viewModel: MainDoctorActivityViewModel by viewModels()
    override fun getVM() = viewModel

    override val layoutId: Int
        get() = R.layout.activity_main_doctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_doctor) as NavHostFragment
        appNavigation.bind(navHostFragment.navController)
    }
}