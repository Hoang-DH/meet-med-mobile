package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.databinding.FragmentDetailAttachmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment

class DetailAttachmentFragment : BaseFragment<FragmentDetailAttachmentBinding, DetailAttachmentViewModel>(R.layout.fragment_detail_attachment) {

    companion object {
        fun newInstance() = DetailAttachmentFragment()
    }

    private val viewModel: DetailAttachmentViewModel by viewModels()
    override fun getVM() = viewModel
    private var mediaUrl = ""
    private var messageType = ""

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadArguments()
        if(messageType == "IMAGE"){
            binding.rlImage.visibility = View.VISIBLE
            binding.rlVideo.visibility = View.GONE
            Glide.with(requireContext()).load(mediaUrl).into(binding.pvImage)

        } else if(messageType == "VIDEO"){
            binding.rlVideo.visibility = View.VISIBLE
            binding.rlImage.visibility = View.GONE
            binding.vvPlayVideo.setVideoPath(mediaUrl)
            binding.vvPlayVideo.start()
        }
    }

    private fun loadArguments() {
        arguments?.let {
            mediaUrl = it.getString(Define.BundleKey.MEDIA_URL, "")
            messageType = it.getString(Define.BundleKey.MESSAGE_TYPE, "")
        }
    }

}