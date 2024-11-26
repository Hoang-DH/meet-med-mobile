package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.BookingShift
import com.example.doctorapp.databinding.FragmentBookingCategoryBinding
import com.example.doctorapp.domain.core.base.BaseFragment

class BookingCategoryFragment :
    BaseFragment<FragmentBookingCategoryBinding, BookingCategoryViewModel>(R.layout.fragment_booking_category) {


    private val viewModel: BookingCategoryViewModel by viewModels()
    override fun getVM() = viewModel
    private var allAppointmentList: List<BookingShift>? = null


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