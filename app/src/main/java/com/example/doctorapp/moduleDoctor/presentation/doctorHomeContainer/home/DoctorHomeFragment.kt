package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.User
import com.example.doctorapp.databinding.FragmentDoctorHomeBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.DoctorHomeContainerFragment
import com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.DoctorHomeContainerViewModel
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorHomeFragment : BaseFragment<FragmentDoctorHomeBinding, DoctorHomeViewModel>(R.layout.fragment_doctor_home) {

    companion object {
        fun newInstance() = DoctorHomeFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: DoctorHomeViewModel by viewModels()
    private val doctorHomeContainerViewModel: DoctorHomeContainerViewModel by activityViewModels()
    override fun getVM() = viewModel
    private var user: User? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Log.d("HoangDH", "DoctorHomeFragment: $doctorHomeContainerViewModel / ${requireParentFragment()}")
        user = Prefs.getInstance(requireContext()).user
        binding.apply {
            tvName.text = String.format(getString(R.string.string_doctor_name), user?.fullName)
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            icRegisterShift.setOnClickListener{
                doctorHomeContainerViewModel.onRegisterShiftClick()
            }
        }
    }
}