package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working.registerNewShift

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentRegisterNewShiftBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.RegisterShiftAdapter

class RegisterNewShiftFragment : BaseFragment<FragmentRegisterNewShiftBinding, RegisterNewShiftViewModel>(R.layout.fragment_register_new_shift) {

    companion object {
        fun newInstance() = RegisterNewShiftFragment()
    }

    private val viewModel: RegisterNewShiftViewModel by viewModels()
    override fun getVM() = viewModel

    private val shiftAdapter by lazy { RegisterShiftAdapter(requireContext()) }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.generateShifts()
        shiftAdapter.submitList(viewModel.shiftList.value)
        binding.apply {
            rvShift.adapter = shiftAdapter
            rvShift.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}