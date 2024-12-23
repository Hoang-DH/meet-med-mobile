package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentDetailAttachmentBinding
import com.example.doctorapp.domain.core.base.BaseFragment

class DetailAttachmentFragment : BaseFragment<FragmentDetailAttachmentBinding, DetailAttachmentViewModel>(R.layout.fragment_detail_attachment) {

    companion object {
        fun newInstance() = DetailAttachmentFragment()
    }

    private val viewModel: DetailAttachmentViewModel by viewModels()
    override fun getVM() = viewModel

}