package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.databinding.FragmentBookingCategoryBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.AppointmentAdapter
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingCategoryFragment :
    BaseFragment<FragmentBookingCategoryBinding, BookingCategoryViewModel>(R.layout.fragment_booking_category) {


    private val viewModel: BookingCategoryViewModel by viewModels()
    override fun getVM() = viewModel
    private var allAppointmentList: List<BookingShift>? = null
    private var appointmentAdapter: AppointmentAdapter? = null


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.getAllPatientAppointments()
        appointmentAdapter = AppointmentAdapter(requireContext())
        binding.apply {
            rvAppointment.adapter = appointmentAdapter
            rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.patientAppointmentResponse.observe(viewLifecycleOwner){ response ->
            when(response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }
                is MyResponse.Success -> {
                    showHideLoading(isShow = false)
                    allAppointmentList = response.data
                    if(allAppointmentList.isNullOrEmpty()) {
                        binding.apply {
                            ivEmptyAppointment.visibility = View.VISIBLE
                            rvAppointment.visibility = View.GONE
                        }

                    } else {
                        binding.ivEmptyAppointment.visibility = View.GONE
                        binding.rvAppointment.visibility = View.VISIBLE
                    }
                    appointmentAdapter?.submitList(allAppointmentList)
                }
                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }
            }
        }
    }


    companion object {
        fun newInstance(category: String): BookingCategoryFragment {
            val fragment = BookingCategoryFragment()
            val bundle = Bundle()
            bundle.putString(Define.Fields.CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }

    }
}