package com.example.doctorapp.modulePatient.presentation.container

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.doctorapp.domain.core.base.BaseActivity
import com.example.doctorapp.R
import com.example.doctorapp.constant.NotificationConstant
import com.example.doctorapp.databinding.ActivityMainBinding
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: MainActivityViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        appNavigation.bind(navHostFragment.navController)
    }

    override val layoutId: Int = R.layout.activity_main


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if(intent.action != NotificationConstant.Type.WORKING_SHIFT_REMINDER){
            appNavigation.openNotificationScreen()
        }
        else appNavigation.openDoctorNotificationScreen()
    }
}