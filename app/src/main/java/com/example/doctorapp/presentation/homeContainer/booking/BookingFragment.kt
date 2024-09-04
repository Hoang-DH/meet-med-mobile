package com.example.doctorapp.presentation.homeContainer.booking

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentBookingBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.adapter.BookingPagerAdapter
import com.example.doctorapp.utils.Define

class BookingFragment :
    BaseFragment<FragmentBookingBinding, BookingViewModel>(R.layout.fragment_booking) {


    private val viewModel: BookingViewModel by viewModels()
    override fun getVM() = viewModel
    private val fragmentList = mutableListOf<Fragment>()

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            vpBooking.adapter = BookingPagerAdapter(this@BookingFragment, viewModel.fragments, viewModel.titles)
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