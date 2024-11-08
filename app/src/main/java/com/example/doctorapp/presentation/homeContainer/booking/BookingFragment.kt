package com.example.doctorapp.presentation.homeContainer.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentBookingBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.adapter.BookingPagerAdapter
import com.example.doctorapp.presentation.homeContainer.booking.bookingCategory.BookingCategoryFragment
import com.example.doctorapp.utils.Define
import com.google.android.material.tabs.TabLayoutMediator

class BookingFragment :
    BaseFragment<FragmentBookingBinding, BookingViewModel>(R.layout.fragment_booking) {


    private val viewModel: BookingViewModel by viewModels()
    override fun getVM() = viewModel
    private val fragmentList = mutableListOf<Fragment>()

    init {
        fragmentList.add(BookingCategoryFragment.newInstance(Define.BookingStatus.UPCOMING))
        fragmentList.add(BookingCategoryFragment.newInstance(Define.BookingStatus.COMPLETED))
        fragmentList.add(BookingCategoryFragment.newInstance(Define.BookingStatus.CANCELLED))
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
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