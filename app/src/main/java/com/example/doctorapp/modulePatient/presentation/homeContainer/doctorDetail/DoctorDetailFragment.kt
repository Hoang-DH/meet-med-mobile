package com.example.doctorapp.modulePatient.presentation.homeContainer.doctorDetail

import androidx.fragment.app.viewModels
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Doctor
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

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val bundle = arguments
        val doctor = bundle?.getParcelable<Doctor>(Define.BundleKey.DOCTOR)
        binding.doctorDetail.apply {
            Glide.with(requireContext())
                .load(doctor?.imageUrl)
                .into(binding.doctorDetail.ivAvatar)
            if (doctor != null) {
                tvDoctorName.text = doctor.user?.fullName ?: "Doctor"
            }
            tvSpeciality.text = doctor?.speciality
            tvRating.text = doctor?.rating.toString()
            if (doctor != null) {
                tvReview.text = when (doctor.reviewCount) {
                    1 -> String.format(
                        getString(R.string.string_review_num),
                        doctor.reviewCount
                    )

                    else -> String.format(
                        getString(R.string.string_review_nums),
                        doctor.reviewCount
                    )
                }
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
            btnBookAppointment.setOnClickListener {
                appNavigation.openDoctorDetailToBookingAppointmentScreen()
            }

        }
    }





}