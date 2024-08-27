package com.example.doctorapp.domain.core.base

import androidx.activity.viewModels
import com.example.chatapp.domain.core.base.BaseActivity
import com.example.doctorapp.R
import com.example.doctorapp.databinding.ActivityBaseWebViewBinding

class BaseWebViewActivity : BaseActivity<ActivityBaseWebViewBinding, BaseWebViewViewModel>() {

    private val viewModel: BaseWebViewViewModel by viewModels()

    override fun getVM() = viewModel

    override val layoutId: Int
        get() = R.layout.activity_base_web_view

    override fun initView() {
        super.initView()

    }

}