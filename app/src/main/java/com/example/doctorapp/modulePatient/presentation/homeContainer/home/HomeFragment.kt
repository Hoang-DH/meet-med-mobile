package com.example.doctorapp.modulePatient.presentation.homeContainer.home

import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.databinding.FragmentHomeBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.DepartmentAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home), DepartmentAdapter.OnDepartmentClickListener {

    @Inject
    lateinit var appNavigation: AppNavigation
    private var mAdapter: DepartmentAdapter? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    override fun getVM() = viewModel
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val fcm =
                    Fcm(Prefs.getInstance(requireContext()).deviceToken, Prefs.getInstance(requireContext()).user?.id)
                viewModel.postFCMDeviceToken(fcm)
            } else {
                requestPermission()
            }
        }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val showRationale = shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)
            if (showRationale) {
                Dialog.showAlertDialog(
                    requireContext(),
                    "Notification permission",
                    "If you do not enable notification permission, you will not receive notifications from appointments, messages and others. Do you want to enable it?",
                    {
                        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    })
            }
        }
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        viewModel.getAllDepartment()
        mAdapter = DepartmentAdapter(requireContext())
        mAdapter?.setOnDepartmentClickListener(this)
        binding.apply {
            rvDepartment.layoutManager =
                androidx.recyclerview.widget.GridLayoutManager(
                    requireContext(),
                    2
                )
            rvDepartment.adapter = mAdapter
            tvName.text = Prefs.getInstance(requireContext()).patient?.user?.fullName ?: "Guest"
            // handle text good morning base on time in day
            tvGreeting.text = getGreeting()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.departmentResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    Prefs.getInstance(requireContext()).department = response.data
                    mAdapter?.submitList(response.data)
                    showHideLoading(false)
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showAlertDialog(requireContext(), "Error", response.exception.message.toString())
                }
            }
        }
    }


    override fun bindingAction() {
        super.bindingAction()
        binding.etSearch.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = true
            setOnClickListener {
                // pass data to know it from home fragment
                val isFromHome = Define.IsFrom.IS_FROM_HOME_SCREEN
                val bundle = Bundle()
                bundle.putString(Define.BundleKey.IS_FROM, isFromHome)
                appNavigation.openHomeContainerToSearchDoctorScreen(bundle)
            }
        }
    }


    // Function to get greeting based on time of day
    private fun getGreeting(): String {
        val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when (currentHour) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    override fun onDepartmentClick(department: Department) {
        val bundle = Bundle()
        bundle.putParcelable(Define.BundleKey.DEPARTMENT, department)
        appNavigation.openHomeContainerToSearchDoctorScreen(bundle)
    }

}