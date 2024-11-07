package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working.registerNewShift

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.data.model.DoctorShift
import com.example.doctorapp.databinding.FragmentRegisterNewShiftBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.RegisterShiftAdapter
import com.example.doctorapp.presentation.utils.DateUtils

class RegisterNewShiftFragment :
    BaseFragment<FragmentRegisterNewShiftBinding, RegisterNewShiftViewModel>(R.layout.fragment_register_new_shift), RegisterShiftAdapter.OnShiftClickListener {

    companion object {
        fun newInstance() = RegisterNewShiftFragment()
    }

    private val viewModel: RegisterNewShiftViewModel by viewModels()
    override fun getVM() = viewModel
//    private var isSelectAll = false
    private val shiftAdapter by lazy {
        RegisterShiftAdapter(requireContext())
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.generateShifts()
        shiftAdapter.submitList(viewModel.shiftList.value)
        shiftAdapter.setOnShiftClickListener(this)
        binding.apply {
            rvShift.adapter = shiftAdapter
            rvShift.layoutManager = LinearLayoutManager(requireContext())
            rvShift.itemAnimator = null
            tvFromDate.text = String.format(getString(R.string.string_from_date),
                viewModel.shiftList.value?.get(0)?.startTime?.let { DateUtils.convertInstantToDate(it) })
            tvToDate.text = String.format(
                getString(R.string.string_to_date),
                viewModel.shiftList.value?.get(viewModel.shiftList.value!!.size - 1)
                    ?.let { DateUtils.convertInstantToDate(it.startTime) })
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            tvSelectAll.setOnClickListener {
                //change text base on isSelectAll
                if (!viewModel.isSelectedAll.value!!) {
                    binding.tvSelectAll.text = getString(R.string.string_clear_all)
                    viewModel.selectAllShift()
                } else {
                    binding.tvSelectAll.text = getString(R.string.string_select_all)
                    viewModel.clearAllShift()
                }
                viewModel.setSelectAll(!viewModel.isSelectedAll.value!!)
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.apply {
            shiftList.observe(viewLifecycleOwner) {
                //check if all list is selected
                if(it.all { shift -> shift.isRegistered }) {
                    viewModel.setSelectAll(true)
                } else {
                    viewModel.setSelectAll(false)
                }
                shiftAdapter.submitList(it)
                shiftAdapter.notifyDataSetChanged()
            }
            isSelectedAll.observe(viewLifecycleOwner) {
                binding.tvSelectAll.text = if (it) getString(R.string.string_clear_all) else getString(R.string.string_select_all)
            }
        }


    }


    override fun onShiftClick(doctorShift: DoctorShift) {
        viewModel.selectShift(doctorShift)
    }

}