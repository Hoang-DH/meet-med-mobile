package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.dto.Fcm
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.data.model.User
import com.example.doctorapp.databinding.FragmentDoctorHomeBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.DoctorAppointmentAdapter
import com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.DoctorHomeContainerViewModel
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorHomeFragment : BaseFragment<FragmentDoctorHomeBinding, DoctorHomeViewModel>(R.layout.fragment_doctor_home), DoctorAppointmentAdapter.OnAppointmentClickListener {

    companion object {
        const val ASC = "asc"
        fun newInstance() = DoctorHomeFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: DoctorHomeViewModel by viewModels()
    private val doctorHomeContainerViewModel: DoctorHomeContainerViewModel by activityViewModels()
    override fun getVM() = viewModel
    private var user: User? = null
    private var mDoctorAppointmentAdapter: DoctorAppointmentAdapter? = null

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
        mDoctorAppointmentAdapter = DoctorAppointmentAdapter(requireContext())
        mDoctorAppointmentAdapter?.setOnAppointmentClickListener(this)
        getAppointments(order = ASC)
        Log.d("HoangDH", "DoctorHomeFragment: $doctorHomeContainerViewModel / ${requireParentFragment()}")
        user = Prefs.getInstance(requireContext()).user
        binding.apply {
            tvName.text = String.format(getString(R.string.string_doctor_name), user?.fullName)
            rvAppointment.adapter = mDoctorAppointmentAdapter
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            icRegisterShift.setOnClickListener{
                doctorHomeContainerViewModel.onFunctionClick(R.id.tab_doctor_working)
            }

            icAppointment.setOnClickListener {
                doctorHomeContainerViewModel.onFunctionClick(R.id.tab_doctor_appointment)
            }

            icEditProfile.setOnClickListener {
                appNavigation.openDoctorHomeToEditProfileScreen()
            }

            tvSeeAll.setOnClickListener {
                doctorHomeContainerViewModel.onFunctionClick(R.id.tab_doctor_appointment)
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.appointmentResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is MyResponse.Loading -> {
                    showHideLoading(true)
                    binding.rvAppointment.visibility = View.GONE
                }
                is MyResponse.Success -> {
                    mDoctorAppointmentAdapter?.submitList(response.data)
                    mDoctorAppointmentAdapter?.notifyDataSetChanged()
                    if(response.data.isEmpty()){
                        binding.layoutEmptyAppointment.visibility = View.VISIBLE
                        binding.rvAppointment.visibility = View.GONE
                        binding.tvSeeAll.visibility = View.GONE
                    } else {
                        binding.layoutEmptyAppointment.visibility = View.GONE
                        binding.rvAppointment.visibility = View.VISIBLE
                        binding.tvSeeAll.visibility = View.VISIBLE
                    }
                    showHideLoading(false)
                }
                is MyResponse.Error -> {
                    binding.rvAppointment.visibility = View.GONE
                    showHideLoading(false)
                }
            }
        }
    }

    private fun getAppointments(orderBy: String = "", order: String = ""){
        val params: MutableMap<String, Any> = HashMap()
        params[Define.Fields.ORDER_BY] = orderBy
        params[Define.Fields.ORDER] = order
        params[Define.Fields.PAGE] = 0.toString()
        params[Define.Fields.SIZE] = 2.toString()
        params[Define.Fields.STATUS] = Define.AppointmentTab.UPCOMING
        viewModel.getAppointments(params)
    }

    override fun onAppointmentClick(doctorAppointment: DoctorAppointment) {
        val bundle = Bundle()
        bundle.putParcelable(Define.BundleKey.DOCTOR_APPOINTMENT, doctorAppointment)
        appNavigation.openDoctorHomeToDoctorDetailAppointmentScreen(bundle)
    }
}