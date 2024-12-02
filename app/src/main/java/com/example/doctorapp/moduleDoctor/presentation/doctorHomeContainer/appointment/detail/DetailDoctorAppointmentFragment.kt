package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.databinding.FragmentDetailAppointmentBinding
import com.example.doctorapp.databinding.FragmentDetailDoctorAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailDoctorAppointmentFragment : BaseFragment<FragmentDetailDoctorAppointmentBinding, DetailDoctorAppointmentViewModel>(R.layout.fragment_detail_doctor_appointment) {

    companion object {
        fun newInstance() = DetailDoctorAppointmentFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: DetailDoctorAppointmentViewModel by viewModels()
    override fun getVM() = viewModel
    private var appointmentId: String? = null
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadArguments()
    }

    private fun loadArguments() {
        val bundle = arguments
        val appointment = bundle?.getParcelable<DoctorAppointment>(Define.BundleKey.DOCTOR_APPOINTMENT)
        val isFrom = bundle?.getString(Define.BundleKey.IS_FROM)
        if (isFrom == Define.AppointmentTab.COMPLETED){
            binding.btnCompleteAppointment.visibility = View.GONE
        }
        if(appointment != null) {
            appointmentId = appointment.id
            bindData(appointment)
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
            btnCompleteAppointment.setOnClickListener {
                appointmentId?.let { it1 -> viewModel.completeAppointment(it1) }
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.completeAppointmentResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is MyResponse.Success -> {
                    showHideLoading(false)
                    Dialog.showCongratulationDialog(requireContext(), "Appointment completed", onClickDone = {
                        appNavigation.navigateUp()
                    })
                }
                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), "Error occurred, please try again !",)
                }
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }
            }
        }
    }

    private fun bindData(appointment: DoctorAppointment) {
        binding.apply {
            appointmentDetail.apply {
                divider2.visibility = View.GONE
                tvPatientName.text = appointment.patient?.user?.fullName
                tvPhoneNumber.text = appointment.patient?.user?.phone
                tvEmail.text = appointment.patient?.user?.email
                tvDate.text = appointment.timeSlot?.startTime?.let { DateUtils.convertInstantToDate(it, "MMMM d, yyyy - HH:mm") }
                context?.let {
                    tvSymptom.text = String.format(
                        it.getString(R.string.string_symptoms_placeholder),
                        appointment.symptoms
                    )
                    Glide.with(it)
                        .load(appointment.patient?.user?.imageUrl)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.ic_profile_pic)
                                .error(R.drawable.ic_profile_pic)
                                .circleCrop()
                        )
                        .into(ivAvatar)
                }
            }
            tvSymptom.text = appointment.symptoms
            tvFullName.text = appointment.patient?.user?.fullName
            tvPhoneNumber.text = appointment.patient?.user?.phone
            tvEmail.text = appointment.patient?.user?.email
            tvGender.text = appointment.patient?.user?.gender?.value
            tvAddressLine.text = appointment.patient?.addressLine
            tvDob.text = appointment.patient?.dob
            tvAge.text = appointment.patient?.user?.age.toString()
            tvDistrict.text = appointment.patient?.district
            tvCity.text = appointment.patient?.city
            tvInsurance.text = appointment.patient?.insuranceCode
        }
    }

}