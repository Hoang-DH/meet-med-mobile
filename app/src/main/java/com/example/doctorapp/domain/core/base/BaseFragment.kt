package com.example.chatapp.domain.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class BaseFragment<BD: ViewDataBinding, VM: BaseViewModel>(@LayoutRes id: Int): BaseFragmentNotRequiredViewModel<BD>(id) {

    private lateinit var viewModel: VM
    abstract fun getVM(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getVM()
    }
}