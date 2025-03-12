package com.example.doctorapp.modulePatient.presentation.homeContainer.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentBookingBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.BookingPagerAdapter
import com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory.BookingCategoryFragment
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment :
    BaseFragment<FragmentBookingBinding, BookingViewModel>(R.layout.fragment_booking) {
    private val viewModel: BookingViewModel by viewModels()
    override fun getVM() = viewModel
    private val fragmentList = mutableListOf<Fragment>()
    private var upcomingList: List<BookingShift>? = null
    private var completedList: List<BookingShift>? = null
    private var cancelledList: List<BookingShift>? = null
    private var allAppointmentList: List<BookingShift>? = null
    init {
        fragmentList.add(BookingCategoryFragment.newInstance(Define.BookingStatus.UPCOMING))
        fragmentList.add(BookingCategoryFragment.newInstance(Define.BookingStatus.COMPLETED))
//        fragmentList.add(BookingCategoryFragment.newInstance(Define.BookingStatus.CANCELLED))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
//        viewModel.getAllPatientAppointments()
        binding.apply {
            vpBooking.adapter = BookingPagerAdapter(
                this@BookingFragment,
                fragmentList,
                resources.getStringArray(R.array.booking_status).toList()
            )
            TabLayoutMediator(tlBooking, vpBooking) { tab, position ->
                tab.text =
                    resources.getStringArray(R.array.booking_status)[position]
            }.attach()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

    }

//    companion object {
//        fun newInstance(category: String): BookingFragment{
//            val fragment = BookingFragment()
//            val bundle = Bundle()
//            bundle.putString(Define.Fields.CATEGORY, category)
//            fragment.arguments = bundle
//            return fragment
//        }
//    }

}