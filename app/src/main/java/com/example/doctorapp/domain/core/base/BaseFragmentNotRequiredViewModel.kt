package com.example.doctorapp.domain.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.doctorapp.utils.CheckNetWorkCallback
import com.example.doctorapp.utils.DeviceUtil
import com.example.doctorapp.utils.Dialog

abstract class BaseFragmentNotRequiredViewModel<BD: ViewDataBinding>(@LayoutRes id: Int): Fragment(id) {

    private var _binding: BD? = null
    protected val binding: BD
        get() = _binding
            ?: throw IllegalStateException("Cannot access view after view destroyed or before view created")



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view)
        _binding!!.lifecycleOwner = viewLifecycleOwner

        if(savedInstanceState == null){
            onInit()
        }
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            onInit(savedInstanceState)
        }
    }
    private fun onInit(savedInstanceState: Bundle? = null){
        initView(savedInstanceState)
        setOnClick()
        bindingStateView()
        bindingAction()
    }

    open fun setOnClick(){

    }

    open fun initView(savedInstanceState: Bundle?){

    }

    open fun bindingStateView() {

    }

    open fun bindingAction() {

    }

    override fun onDestroyView() {
        _binding?.unbind()
        _binding = null
        super.onDestroyView()
    }

    fun showHideLoading(isShow: Boolean) {
        if (activity != null && activity is BaseActivityNotRequiredViewModel<*>) {
            if (isShow) {
                (activity as BaseActivityNotRequiredViewModel<*>?)!!.showLoading()
            } else {
                (activity as BaseActivityNotRequiredViewModel<*>?)!!.hideLoading()
            }
        }
    }

    protected fun checkAndHandleNetworkConnect(callback: CheckNetWorkCallback){
        if(DeviceUtil.isNetworkConnected(requireContext())) {
            callback.networkConnected()
        } else{
            Dialog.showDialogError(requireContext(), "No internet connection"){
                if(DeviceUtil.isNetworkConnected(requireContext())){
                    callback.networkConnected()
                } else{
                    checkAndHandleNetworkConnect(callback)
                }
            }
        }
    }

//    fun showDatePickerDialog(fullSlotDay: List<Long>) {
//        if (activity != null && activity is BaseActivityNotRequiredViewModel<*>) {
//            (activity as BaseActivityNotRequiredViewModel<*>?)!!.showDatePickerDialog(fullSlotDay)
//        }
//    }

}