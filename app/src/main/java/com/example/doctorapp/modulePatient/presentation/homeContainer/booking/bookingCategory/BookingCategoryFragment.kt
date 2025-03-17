package com.example.doctorapp.modulePatient.presentation.homeContainer.booking.bookingCategory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.model.BookingShift
import com.example.doctorapp.databinding.FragmentBookingCategoryBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.AppointmentAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookingCategoryFragment :
    BaseFragment<FragmentBookingCategoryBinding, BookingCategoryViewModel>(R.layout.fragment_booking_category),
    AppointmentAdapter.OnAppointmentClickListener {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: BookingCategoryViewModel by viewModels()
    override fun getVM() = viewModel
    private var allAppointmentList: List<BookingShift>? = null
    private var appointmentAdapter: AppointmentAdapter? = null
    private var currentTab: String = Define.BookingStatus.UPCOMING
    private var currentPage  = 0

    private fun loadArguments() {
        val bundle = arguments
        val category = bundle?.getString(Define.Fields.CATEGORY)
        if (category != null) {
            currentTab = category
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadArguments()
        currentPage = 0
        getAppointmentList(page = currentPage, status = currentTab)
        appointmentAdapter = AppointmentAdapter(requireContext())
        appointmentAdapter?.setOnAppointmentClickListener(this)
        if(appointmentAdapter?.getLoadMorelistener() == null) {
            appointmentAdapter?.setLoadMorelistener(object : BaseAdapterLoadMore.LoadMorelistener {
                override fun onLoadMore() {
                    loadMore()
                }
            })
        }
        binding.apply {
            rvAppointment.adapter = appointmentAdapter
            rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadMore() {
        currentPage++
        getAppointmentList(page = currentPage, status = currentTab)
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.patientAppointmentResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(isShow = false)
                    allAppointmentList = response.data
                    if (allAppointmentList.isNullOrEmpty()) {
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

    private fun getAppointmentList(page: Int = 0, status: String = "", order: String = "desc", orderBy: String = "createdAt") {
        val params = mutableMapOf<String, Any>()
        params[Define.Fields.PAGE] = page.toString()
        params[Define.Fields.STATUS] = status
        params[Define.Fields.ORDER] = order
        params[Define.Fields.ORDER_BY] = orderBy
        viewModel.getAllPatientAppointments(params)

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

    override fun onAppointmentClick(bookingShift: BookingShift) {
        val bundle = Bundle()
        bundle.putParcelable(Define.BundleKey.BOOKING_SHIFT, bookingShift)
        appNavigation.openBookingCategoryToDetailAppointmentScreen(bundle)
    }
}