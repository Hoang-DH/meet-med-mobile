package com.example.doctorapp.modulePatient.presentation.homeContainer.profile.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.domain.model.Notification
import com.example.doctorapp.domain.model.NotificationData
import com.example.doctorapp.databinding.FragmentNotificationBinding
import com.example.doctorapp.domain.core.base.BaseAdapterLoadMore
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.NotificationAdapter
import com.example.doctorapp.modulePatient.presentation.loadMore.LoadMoreView
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import dagger.hilt.android.AndroidEntryPoint
import org.cloudinary.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment :
    BaseFragment<FragmentNotificationBinding, NotificationViewModel>(R.layout.fragment_notification), NotificationAdapter.OnNotificationClickListener {

    companion object {
        const val DESC = "desc"
        fun newInstance() = NotificationFragment()
    }

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: NotificationViewModel by viewModels()
    private var notificationAdapter: NotificationAdapter? = null
    override fun getVM() = viewModel
    private var currentPage = 0
    private lateinit var loadMoreView: LoadMoreView



    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        currentPage = 0
        notificationAdapter = NotificationAdapter(requireContext())
        notificationAdapter?.setOnNotificationClickListener(this)
        getListNotificationFromServer(currentPage)
        loadMoreView = LoadMoreView(context)
        if (notificationAdapter?.getLoadMorelistener() == null) {
            notificationAdapter?.setLoadMorelistener(object : BaseAdapterLoadMore.LoadMorelistener {
                override fun onLoadMore() {
//                    binding.rvNotification.post {
//                        notificationAdapter.addFooter(loadMoreView)
//                    }
                    loadMore()
                }
            })
        }
        binding.apply {
            rvNotification.adapter = notificationAdapter
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
        }
    }

    private fun loadMore() {
        loadMoreView.startLoadingAnimation()
        getListNotificationFromServer(currentPage)
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.notificationResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    currentPage++
//                    notificationAdapter.setNotiList(notificationList)
                    if(response.data.isEmpty()){
                        binding.layoutEmptyNotification.visibility = View.VISIBLE
                        binding.rvNotification.visibility = View.GONE
                    }
                    else{
                        binding.layoutEmptyNotification.visibility = View.GONE
                        binding.rvNotification.visibility = View.VISIBLE
                        notificationAdapter?.submitList(response.data)
                        notificationAdapter?.notifyDataSetChanged()
                    }
                    loadMoreView.stopLoadingAnimation()
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), response.exception.message.toString())
                }
            }
        }

        viewModel.isMarkAsReadSuccess.observe(viewLifecycleOwner) { response ->
            if(response){
                notificationAdapter?.notifyDataSetChanged()
            }
        }
    }


    private fun getListNotificationFromServer(page: Int) {
        val params: MutableMap<String, String> = HashMap()
        params[Define.Fields.PAGE] = page.toString()
        params[Define.Fields.ORDER] = DESC
        viewModel.getListNotification(params)
    }

    override fun onNotificationClick(notification: NotificationData) {
        notification.id?.let { viewModel.markNotificationAsRead(it) }
        val objectDataString = notification.objectData
        val objectJson = JSONObject(objectDataString)
        val objectMap = objectJson.keys().asSequence().associateWith { objectJson.getString(it) }
        val bundle = Bundle()
        bundle.putString(Define.BundleKey.APPOINTMENT_ID, objectMap["appointmentId"])
        Log.d("HoangDH", "onNotificationClick: ${objectMap["appointmentId"]}")
        appNavigation.openNotificationToDetailAppointmentScreen(bundle)
    }
}