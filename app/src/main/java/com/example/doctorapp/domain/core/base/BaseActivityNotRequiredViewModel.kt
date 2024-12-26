package com.example.doctorapp.domain.core.base

import android.os.Bundle
import android.view.MotionEvent
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.doctorapp.utils.CheckNetWorkCallback
import com.example.doctorapp.utils.DeviceUtil
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.LoadingDialog
import java.lang.ref.WeakReference

abstract class BaseActivityNotRequiredViewModel<BD: ViewDataBinding> : AppCompatActivity() {

    private var _binding: BD? = null
    protected val binding: BD get() = _binding!!

    @get: LayoutRes
    abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(WeakReference(this).get()!!, layoutId)
        _binding?.lifecycleOwner = this
        initView()
        setOnClick()
        bindingStateView()
        bindingAction()

    }

    open fun initView() {}
    open fun setOnClick() {}
    open fun bindingStateView() {}
    open fun bindingAction() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding?.unbind()
        _binding = null
        LoadingDialog.getInstance(this)?.destroyLoadingDialog()
    }



    fun showLoading() {
        LoadingDialog.getInstance(this)?.show()
    }

    fun hideLoading() {
        LoadingDialog.getInstance(this)?.hidden()
    }



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}