package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.model.DoctorAppointment
import com.example.doctorapp.domain.model.TimeSlot
import com.example.doctorapp.databinding.FragmentDoctorAppointmentCategoryBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.DoctorAppointmentAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorAppointmentCategoryFragment : BaseFragment<FragmentDoctorAppointmentCategoryBinding, DoctorAppointmentCategoryViewModel>(R.layout.fragment_doctor_appointment_category), DoctorAppointmentAdapter.OnAppointmentClickListener {


    @Inject
    lateinit var appNavigation: AppNavigation

    companion object {
        const val ASC = "asc"
        fun newInstance(category: String): DoctorAppointmentCategoryFragment {
            val fragment = DoctorAppointmentCategoryFragment()
            val bundle = Bundle()
            bundle.putString(Define.Fields.CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: DoctorAppointmentCategoryViewModel by viewModels()
    override fun getVM() = viewModel
    private var mAppointmentAdapter: DoctorAppointmentAdapter? = null
    private var currentTab: String = Define.AppointmentTab.UPCOMING
    private var currentPage = 0

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadArguments()
        currentPage = 0
        getAppointments(currentTab, currentPage)
        mAppointmentAdapter = DoctorAppointmentAdapter(requireContext())
        mAppointmentAdapter?.setOnAppointmentClickListener(this)
        if (mAppointmentAdapter?.getLoadMorelistener() == null) {
            mAppointmentAdapter?.setLoadMorelistener(object : BaseAdapterLoadMore.LoadMorelistener {
                override fun onLoadMore() {
//                    binding.rvNotification.post {
//                        notificationAdapter.addFooter(loadMoreView)
//                    }
                    loadMore()
                }
            })
        }
        binding.apply {
            rvAppointment.adapter = mAppointmentAdapter
            rvAppointment.layoutManager = LinearLayoutManager(requireContext())
            swipeRefreshLayout.setOnRefreshListener {
                currentPage = 0
                getAppointments(currentTab, currentPage)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun loadMore() {
        currentPage++
        getAppointments(page = currentPage, status = currentTab)
    }

    private fun loadArguments() {
        currentTab = arguments?.getString(Define.Fields.CATEGORY) ?: Define.AppointmentTab.UPCOMING
    }


    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.appointmentResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(isShow = false)

                    if (response.data.isEmpty()) {
                        binding.apply {
                            layoutEmptyAppointment.visibility = android.view.View.VISIBLE
                            swipeRefreshLayout.visibility = android.view.View.GONE
                        }
                    } else {
                        binding.apply {
                            layoutEmptyAppointment.visibility = android.view.View.GONE
                            swipeRefreshLayout.visibility = android.view.View.VISIBLE
                            mAppointmentAdapter?.submitList(response.data)
                            mAppointmentAdapter?.notifyDataSetChanged()
                        }
                    }
                }

                is MyResponse.Error -> {
                    showHideLoading(isShow = false)
                    Dialog.showDialogError(requireContext(), "Error occurred. Please try again later.")
                }
            }
        }
    }

    //create function to generate list of appointment

    private fun getAppointments(status: String = "", page : Int = 0) {
        val params: MutableMap<String, Any> = mutableMapOf()
        params[Define.Fields.STATUS] = status
        params[Define.Fields.ORDER] = ASC
        params[Define.Fields.PAGE] = page.toString()
        viewModel.getAppointments(params)
    }

    override fun onAppointmentClick(doctorAppointment: DoctorAppointment) {
        val bundle = Bundle()
        bundle.putString(Define.BundleKey.IS_FROM, currentTab)
        bundle.putParcelable(Define.Fields.DOCTOR_APPOINTMENT, doctorAppointment)
        appNavigation.openDoctorHomeToDoctorDetailAppointmentScreen(bundle)
    }
}