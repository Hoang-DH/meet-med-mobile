package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingAppointment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.DoctorBookingShift
import com.example.doctorapp.data.model.TimeSlot
import com.example.doctorapp.databinding.FragmentBookingAppointmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.BookingShiftAdapter
import com.example.doctorapp.modulePatient.presentation.adapter.TimeSlotAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookingAppointmentFragment :
    BaseFragment<FragmentBookingAppointmentBinding, BookingAppointmentViewModel>(R.layout.fragment_booking_appointment),
    BookingShiftAdapter.OnShiftClick,
    TimeSlotAdapter.OnTimeSlotClick {

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
    private var bookedTimeSlot: TimeSlot? = null
    private var doctorBookingShiftList: ArrayList<DoctorBookingShift>? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val bundle = arguments
        doctor = bundle?.getParcelable(Define.BundleKey.DOCTOR)
        doctor?.id?.let { Log.e("HoangDH", it) }
        viewModel.getDoctorBookingShifts(doctor?.id ?: "")
        mBookingShiftAdapter = BookingShiftAdapter()
        mBookingShiftAdapter?.setOnShiftClick(this)
        mTimeSlotAdapter = TimeSlotAdapter()
        mTimeSlotAdapter?.setOnTimeSlotClick(this)

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

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            btnBookAppointment.setOnClickListener {
                viewModel.bookAppointment(BookingShift(null, edtSymptom.text.toString(), bookedTimeSlot))
            }
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
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
                    doctorBookingShiftList = ArrayList(response.data)
                    processData()
                    mBookingShiftAdapter?.submitList(doctorBookingShiftList)
                    if(doctorBookingShiftList?.size!! > 0 ) {
                        mTimeSlotAdapter?.submitList(doctorBookingShiftList!![0].timeSlot)
                        bookedTimeSlot = doctorBookingShiftList!![0].timeSlot?.get(0)
                        binding.apply {
                            svBookingAppointment.visibility = android.view.View.VISIBLE
                            layoutEmptyNotification.visibility = android.view.View.GONE
                        }
                    } else {
                        binding.apply {
                            svBookingAppointment.visibility = android.view.View.GONE
                            layoutEmptyNotification.visibility = android.view.View.VISIBLE
                        }
                    }
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }
            }
        }

        viewModel.bookingAppointmentResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    Dialog.showCongratulationDialog(
                        requireContext(),
                        "Your appointment has been booked successfully",
                        {
                            appNavigation.openBookingAppointmentToMyBookingScreen()
                        })
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }
            }
        }
    }


    override fun onShiftClick(bookingShift: DoctorBookingShift) {
        mTimeSlotAdapter?.submitList(bookingShift.timeSlot)
    }

    override fun onTimeSlotClick(timeSlot: TimeSlot) {
        bookedTimeSlot = timeSlot
        Log.e("HoangDH", timeSlot.toString())
    }

    private fun processData() {

        if(doctorBookingShiftList?.size == 1){
            return
        }

        for (i in 0 until doctorBookingShiftList!!.size) {
            if (i < doctorBookingShiftList!!.size-1) {
                if (doctorBookingShiftList!![i].shift?.startTime?.let {
                        DateUtils.convertInstantToDayOfWeek(
                            it
                        )
                    } == doctorBookingShiftList!![i + 1].shift?.startTime?.let {
                        DateUtils.convertInstantToDayOfWeek(
                            it
                        )
                    }) {
                    doctorBookingShiftList!![i].timeSlot?.addAll(doctorBookingShiftList!![i + 1].timeSlot!!)
                    doctorBookingShiftList!!.remove(doctorBookingShiftList!![i + 1])
                    Log.e(
                        "HoangDH",
                        doctorBookingShiftList!![i].timeSlot?.get(0)?.startTime.toString()
                    )
                }
            }

        }
    }

}