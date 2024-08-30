package com.example.doctorapp.presentation.splash

import androidx.fragment.app.viewModels
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSplashBinding
import com.example.doctorapp.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    companion object {
        fun newInstance() = SplashFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: SplashViewModel by viewModels()

    override fun getVM() = viewModel

    override fun bindingAction() {
        super.bindingAction()
        viewModel.actionSplash.observe(viewLifecycleOwner){splashState ->
            if (splashState == SplashActionState.Finish){
                appNavigation.openSplashToSignInScreen()
            }
        }
    }

}