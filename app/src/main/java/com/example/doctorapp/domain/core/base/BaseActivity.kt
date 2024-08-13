package com.example.chatapp.domain.core.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<BD: ViewDataBinding, VM: BaseViewModel>: BaseActivityNotRequiredViewModel<BD>() {
    private lateinit var viewModel: VM
    abstract fun getVM(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getVM()
    }

//    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        if (event != null) {
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                val v = currentFocus
//                if (v is EditText) {
//                    val outRect = Rect()
//                    v.getGlobalVisibleRect(outRect)
//                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
//                        v.clearFocus()
//                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                        imm.hideSoftInputFromWindow(v.windowToken, 0)
//                    }
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event)
//    }

}