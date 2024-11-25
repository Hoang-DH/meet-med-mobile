package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingAppointment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.DoctorBookingShift
import com.example.doctorapp.databinding.FragmentBookingAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.BookingShiftAdapter
import com.example.doctorapp.modulePatient.presentation.adapter.TimeSlotAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookingAppointmentFragment :
    BaseFragment<FragmentBookingAppointmentBinding, BookingAppointmentViewModel>(R.layout.fragment_booking_appointment),
    BookingShiftAdapter.OnShiftClick {

    companion object {
        fun newInstance() = BookingAppointmentFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: BookingAppointmentViewModel by viewModels()
    override fun getVM() = viewModel

    private var mBookingShiftAdapter: BookingShiftAdapter? = null
    private var mTimeSlotAdapter: TimeSlotAdapter? = null
    private var doctor: Doctor? = null


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val bundle = arguments
        doctor = bundle?.getParcelable(Define.BundleKey.DOCTOR)
        doctor?.id?.let { Log.e("HoangDH", it) }
        viewModel.getDoctorBookingShifts(doctor?.id ?: "")
        mBookingShiftAdapter = BookingShiftAdapter()
        mBookingShiftAdapter?.setOnShiftClick(this)
        mTimeSlotAdapter = TimeSlotAdapter()

        binding.apply {
            rvDate.apply {
                adapter = mBookingShiftAdapter
                itemAnimator = null
                layoutManager = GridLayoutManager(requireContext(), 3)
            }

            rvTime.apply {
                adapter = mTimeSlotAdapter
                itemAnimator = null
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.doctorBookingShiftLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    mBookingShiftAdapter?.submitList(response.data)
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }
            }

        }
    }

    override fun setOnClick() {
        super.setOnClick()

    }

    override fun onShiftClick(bookingShift: DoctorBookingShift) {
        mTimeSlotAdapter?.submitList(bookingShift.timeSlot)
    }


}