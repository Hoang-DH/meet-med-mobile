package com.example.doctorapp.presentation.homeContainer.booking.bookingCategory

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.presentation.homeContainer.booking.BookingFragment
import com.example.doctorapp.utils.Define

class BookingCategoryFragment : Fragment() {


    private val viewModel: BookingCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_booking_category,
            container,
            false
        )
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