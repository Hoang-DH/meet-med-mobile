package com.example.doctorapp.domain.core.base

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.ActivityBaseWebViewBinding

class BaseWebViewActivity : BaseActivity<ActivityBaseWebViewBinding, BaseWebViewViewModel>() {

    private val viewModel: BaseWebViewViewModel by viewModels()

    override fun getVM() = viewModel

    override val layoutId: Int
        get() = R.layout.activity_base_web_view

    override fun initView() {
        super.initView()
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    showLoading()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    hideLoading()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                }
            }
        }


    }

}