package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.working

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.domain.model.DoctorShift
import com.example.doctorapp.databinding.FragmentWorkingCategoryBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.DoctorShiftAdapter
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.constant.Define
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkingCategoryFragment :
    BaseFragment<FragmentWorkingCategoryBinding, WorkingCategoryViewModel>(R.layout.fragment_working_category),
    DoctorShiftAdapter.OnShiftClickListener {

    companion object {
        fun newInstance(category: String): WorkingCategoryFragment {
            val fragment = WorkingCategoryFragment()
            val bundle = Bundle()
            bundle.putString(Define.Fields.CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: WorkingCategoryViewModel by viewModels()
    override fun getVM() = viewModel
    private var tab: String = Define.WorkingTab.REGISTER_NEW_SHIFT
    private val shiftAdapter by lazy {
        DoctorShiftAdapter(requireContext(), tab == Define.WorkingTab.MY_SHIFTS)
    }

    private var isShowDialog = true





    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        tab = arguments?.getString(Define.Fields.CATEGORY).toString()
        if(tab == Define.WorkingTab.MY_SHIFTS){
            viewModel.getRegisteredShifts()
        } else {
            viewModel.getListShiftToRegister()
        }
        shiftAdapter.setOnShiftClickListener(this)
        binding.apply {
            rvShift.adapter = shiftAdapter
            rvShift.layoutManager = LinearLayoutManager(requireContext())
            rvShift.itemAnimator = null
            if(tab == Define.WorkingTab.MY_SHIFTS){
                btnAddShift.visibility = View.GONE
                tvSelectAll.visibility = View.GONE
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            tvSelectAll.setOnClickListener {
                //change text base on isSelectAll
                if (!viewModel.isSelectedAll.value!!) {
                    binding.tvSelectAll.text = getString(R.string.string_clear_all)
                    viewModel.selectAllShift(tab)
                } else {
                    binding.tvSelectAll.text = getString(R.string.string_select_all)
                    viewModel.clearAllShift(tab)
                }
            }
            btnAddShift.setOnClickListener {
                viewModel.registerNewShift()
            }
            swipeRefreshLayout.setOnRefreshListener {
                if(tab == Define.WorkingTab.MY_SHIFTS){
                    viewModel.getRegisteredShifts()
                } else {
                    viewModel.getListShiftToRegister()
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.apply {
            shiftListResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Success -> {
                        showHideLoading(false)
                        binding.apply {
                           if(response.data.isNotEmpty()){
                               rvShift.visibility = View.VISIBLE
                               layoutEmptyRegisteredShift.visibility = View.GONE
                               tvFromDate.visibility = View.VISIBLE
                               tvToDate.visibility = View.VISIBLE
                               tvFromDate.text = String.format(getString(R.string.string_from_date),
                                   response.data[0].startTime.let { DateUtils.convertInstantToDate(it, "dd/MM/yyyy") })
                               tvToDate.text = String.format(
                                   getString(R.string.string_to_date),
                                   response.data[response.data.size - 1]
                                       .let { DateUtils.convertInstantToDate(it.startTime, "dd/MM/yyyy") })
                               if (response.data.all { shift -> shift.isRegistered }) {
                                   viewModel.setSelectAll(true)
                               } else {
                                   viewModel.setSelectAll(false)
                               }
                               val processedData = viewModel.processShiftList(response.data)
                               shiftAdapter.submitList(processedData)
                               shiftAdapter.notifyDataSetChanged()
                           }
                            else {
                               rvShift.visibility = View.GONE
                               layoutEmptyRegisteredShift.visibility = View.VISIBLE
                               tvFromDate.visibility = View.GONE
                               tvToDate.visibility = View.GONE
                               tvSelectAll.visibility = View.GONE
                            }
                        }
                    }

                    is MyResponse.Error -> {
                        showHideLoading(false)
                        Utils.showSnackBar(response.exception.toString(), binding.root)
                    }

                    is MyResponse.Loading -> {
                        showHideLoading(true)
                    }
                }

            }

            isSelectedAll.observe(viewLifecycleOwner) {
                binding.tvSelectAll.text =
                    if (it) getString(R.string.string_clear_all) else getString(R.string.string_select_all)
            }

            registeredShiftResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is MyResponse.Success -> {
                        showHideLoading(false)
                        if(isShowDialog){
                            Dialog.showCongratulationDialog(
                                requireContext(),
                                "Register shift successfully",
                                onClickDone = {
                                    // navigate to MY_SHIFTS tab
                                    (requireParentFragment() as DoctorWorkingFragment).changeTab(Define.WorkingTab.MY_SHIFTS)
                                    // hide dialog
                                }

                            )
                        }
                    }

                    is MyResponse.Error -> {
                        showHideLoading(false)
                        Dialog.showDialogError(requireContext(),"Error occurred, please try again later")
                    }

                    is MyResponse.Loading -> {
                       showHideLoading(true)
                    }
                }
            }

        }


    }
    override fun onShiftClick(shift: DoctorShift) {
        viewModel.selectShift(shift)
    }
}