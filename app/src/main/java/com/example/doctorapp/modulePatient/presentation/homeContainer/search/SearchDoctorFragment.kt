package com.example.doctorapp.modulePatient.presentation.homeContainer.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.databinding.FragmentSearchDoctorBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.DepartmentCategoryAdapter
import com.example.doctorapp.modulePatient.presentation.adapter.SearchDoctorAdapter
import com.example.doctorapp.modulePatient.presentation.constants.SortType
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchDoctorFragment :
    BaseFragment<FragmentSearchDoctorBinding, SearchDoctorViewModel>(R.layout.fragment_search_doctor),
    DepartmentCategoryAdapter.OnDepartmentClickListener {

    @Inject
    lateinit var appNavigation: AppNavigation
    private var mDoctorAdapter: SearchDoctorAdapter? = null
    private var mDepartmentCategoryAdapter: DepartmentCategoryAdapter? = null
    private var mDoctors: ArrayList<Doctor> = arrayListOf()
    private val mDepartments: ArrayList<Department> = arrayListOf()
    private var selectedDepartment: Department? = null
    private var currentPage = 0

    companion object {
        fun newInstance() = SearchDoctorFragment()
        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
        const val ORDER_BY_FULL_NAME = "user.fullName"
        const val ORDER_BY_YOE = "yearsOfExperience"
    }

    private val viewModel: SearchDoctorViewModel by viewModels()
    override fun getVM() = viewModel
    private var isFrom: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mDoctorAdapter = SearchDoctorAdapter(requireContext()) { doctor ->
            val bundle = Bundle()
            bundle.putParcelable(Define.BundleKey.DOCTOR, doctor)
            appNavigation.openSearchDoctorToDoctorDetailScreen(bundle)
        }
        mDepartmentCategoryAdapter = DepartmentCategoryAdapter(requireContext())
        mDepartmentCategoryAdapter?.setOnDepartmentClickListener(this)
        mDepartmentCategoryAdapter?.submitList(mDepartments)
        loadArgument()
        mDepartments.addAll(Prefs.getInstance(requireContext()).department)
        if (isFrom == Define.IsFrom.IS_FROM_HOME_SCREEN) {
            selectedDepartment = mDepartments.firstOrNull()
            searchDoctors(currentPage, "", "", "", selectedDepartment?.id ?: "")
        } else {
            mDepartmentCategoryAdapter?.setSelection(mDepartments.indexOfFirst { it.id == selectedDepartment?.id })
            searchDoctors(department = selectedDepartment?.id ?: "")
            mDepartmentCategoryAdapter?.notifyDataSetChanged()
        }

        binding.apply {
            rvDoctor.apply {
                adapter = mDoctorAdapter
                itemAnimator = null
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            rvCategory.apply {
                adapter = mDepartmentCategoryAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            tvDoctorCount.text =
                String.format(getString(R.string.string_search_doctor_count), mDoctors.size)
            etSearch.setOnEditorActionListener { _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(etSearch.text.toString().isNotEmpty()){
                        searchDoctors(
                            search = etSearch.text.toString(),
                        )
                    } else {
                        searchDoctors(
                            department = selectedDepartment?.id ?: ""
                        )
                    }
                    hideKeyboard()
                    //set sortype to default
                    binding.tvSortType.text = SortType.DEFAULT.value
                    binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_sort_default,
                        0
                    )
                    true
                } else {
                    false
                }
            }
            swipeRefresh.setOnRefreshListener {
                currentPage = 0
                searchDoctors(currentPage, "", "", "", selectedDepartment?.id ?: "")
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isFrom = ""
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.searchDoctorResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    binding.tvDoctorCount.text =
                        String.format(
                            getString(R.string.string_search_doctor_count),
                            response.data.size
                        )
                    mDoctors.clear()
                    mDoctors.addAll(response.data)
                    val departmentIdFromResponse = response.data.firstOrNull()?.department?.id
                    if (departmentIdFromResponse != null) {
                        val departmentPos = mDepartments.indexOfFirst { it.id == departmentIdFromResponse }
                        selectedDepartment = mDepartments.getOrNull(departmentPos)
                        if (departmentPos != -1) {
                            mDepartmentCategoryAdapter?.setSelection(departmentPos)
                            departmentScrollToPosition(departmentPos)
                        }
                    }
                    mDoctorAdapter?.submitList(mDoctors)
                    mDoctorAdapter?.notifyDataSetChanged()
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    Dialog.showDialogError(
                        requireContext(),
                        response.exception.message ?: getString(R.string.string_error)
                    )
                }
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            tvSortType.setOnClickListener {
                Dialog.showSortTypeDialog(
                    onClickSortDefault = {
                        sortDoctors(SortType.DEFAULT)
                    },
                    onClickSortNameAZ = {
                        sortDoctors(SortType.NAME_AZ)
                    }, onClickSortNameZA = {
                        sortDoctors(SortType.NAME_ZA)
                    }, onClickSortYoeDes = {
                        sortDoctors(SortType.YOE_DESC)
                    }, onClickSortYoeAsc = {
                        sortDoctors(SortType.YOE_ASC)
                    },
                    view = tvSortType
                )
            }
            ivBack.setOnClickListener {
                appNavigation.openSearchDoctorToHomeContainerScreen()
            }
        }
    }

    private fun loadArgument() {
        arguments?.let {
            isFrom = it.getString(Define.BundleKey.IS_FROM)
            selectedDepartment = it.getParcelable(Define.BundleKey.DEPARTMENT)
        }
        // clear argument
        arguments = null
    }

    private fun sortDoctors(sortType: SortType) {
        binding.tvSortType.text = sortType.value
        val params: MutableMap<String, Any> = HashMap()
        when (sortType) {
            SortType.NAME_AZ -> {
                searchDoctors(order = ORDER_ASC, orderBy = ORDER_BY_FULL_NAME, department = selectedDepartment?.id ?: "")
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            }

            SortType.NAME_ZA -> {
                searchDoctors(order = ORDER_DESC, orderBy = ORDER_BY_FULL_NAME, department = selectedDepartment?.id ?: "")
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }

            SortType.YOE_ASC -> {
                searchDoctors(order = ORDER_ASC, orderBy = ORDER_BY_YOE, department = selectedDepartment?.id ?: "")
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            }

            SortType.YOE_DESC -> {
                searchDoctors(order = ORDER_DESC, orderBy = ORDER_BY_YOE, department = selectedDepartment?.id ?: "")
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }

            else -> {
                searchDoctors(department = selectedDepartment?.id ?: "")
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_sort_default,
                    0
                )
            }
        }
        scrollToTop()
    }

    private fun searchDoctors(
        page: Int = 0,
        order: String = "",
        orderBy: String = "",
        search: String = "",
        department: String = ""
    ) {
        val params: MutableMap<String, Any> = HashMap()
        params[Define.Fields.PAGE] = page
        params[Define.Fields.ORDER] = order
        params[Define.Fields.ORDER_BY] = orderBy
        params[Define.Fields.SEARCH] = search
        params[Define.Fields.DEPARTMENT] = department
        viewModel.searchDoctor(params)

    }

    private fun scrollToTop() {
        //delay to scroll to top after list is updated
        binding.rvDoctor.postDelayed({
            binding.rvDoctor.smoothScrollToPosition(0)
        }, 100)

    }

    private fun departmentScrollToPosition(position: Int){
        binding.rvCategory.postDelayed({
            binding.rvCategory.smoothScrollToPosition(position)
        }, 100)
    }

    override fun onDepartmentClick(department: Department) {
        selectedDepartment = department
        val params: MutableMap<String, Any> = HashMap()
        params[Define.Fields.DEPARTMENT] = department.id ?: ""
        viewModel.searchDoctor(params)
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}