package com.example.doctorapp.modulePatient.presentation.homeContainer.doctorDetail

import androidx.fragment.app.viewModels
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.doctorapp.R
import com.example.doctorapp.domain.model.Doctor
import com.example.doctorapp.databinding.FragmentDoctorDetailBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.constant.Define
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorDetailFragment : BaseFragment<FragmentDoctorDetailBinding, DoctorDetailViewModel>(R.layout.fragment_doctor_detail) {

    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        fun newInstance() = DoctorDetailFragment()
    }

    private val viewModel: DoctorDetailViewModel by viewModels()
    override fun getVM() = viewModel
    private var doctor : Doctor? = null
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val bundle = arguments
        doctor = bundle?.getParcelable(Define.BundleKey.DOCTOR)
        binding.doctorDetail.apply {
            Glide.with(requireContext())
                .load(doctor?.user?.imageUrl)
                .into(binding.doctorDetail.ivAvatar)
            if (doctor != null) {
                tvDoctorName.text = doctor?.user?.fullName ?: "Doctor"
            }
            tvSpeciality.text = doctor?.department?.name
            if(doctor?.yearsOfExperience != null){
                tvYoe.text = context?.getString(R.string.string_number_of_yoe)
                    ?.let { String.format(it, doctor?.yearsOfExperience) }
                binding.doctorBadge.apply {
                    tvYoe.text = doctor?.yearsOfExperience.toString()
                    tvYoe.visibility = android.view.View.VISIBLE
                    icYoe.visibility = android.view.View.VISIBLE
                    tvCert.visibility = android.view.View.VISIBLE
                }
                tvYoe.visibility = android.view.View.VISIBLE
                ivStar.visibility = android.view.View.VISIBLE
            } else {
                tvYoe.visibility = android.view.View.GONE
                ivStar.visibility = android.view.View.GONE
                binding.doctorBadge.tvYoe.visibility = android.view.View.GONE
                binding.doctorBadge.icYoe.visibility = android.view.View.GONE
                binding.doctorBadge.tvCert.visibility = android.view.View.GONE
            }
        }
        binding.doctorBadge.tvNumberOfPatients.text = doctor?.numberOfPatients.toString()
        binding.tvAboutContent.text = doctor?.description
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
            btnBookAppointment.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(Define.BundleKey.DOCTOR, doctor)
                appNavigation.openDoctorDetailToBookingAppointmentScreen(bundle)
            }

        }
    }





}