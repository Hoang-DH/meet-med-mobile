package com.example.doctorapp.modulePatient.presentation.homeContainer.search

import android.os.Bundle
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

    companion object {
        fun newInstance() = SearchDoctorFragment()
        const val ORDER_ASC = "asc"
        const val ORDER_DESC = "desc"
        const val ORDER_BY_FULL_NAME = "user.fullName"
        const val   ORDER_BY_YOE = "yearsOfExperience"
    }

    private val viewModel: SearchDoctorViewModel by viewModels()
    override fun getVM() = viewModel
    private var isFrom: String? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadArgument()
        if (isFrom == Define.IsFrom.IS_FROM_HOME_SCREEN) {
            viewModel.searchDoctor(mapOf())
        }
        mDoctorAdapter = SearchDoctorAdapter(requireContext()) { doctor ->
            val bundle = Bundle()
            bundle.putParcelable(Define.BundleKey.DOCTOR, doctor)
            appNavigation.openSearchDoctorToDoctorDetailScreen(bundle)
        }
        mDepartmentCategoryAdapter = DepartmentCategoryAdapter(requireContext())
        mDepartmentCategoryAdapter?.setOnDepartmentClickListener(this)
        mDepartmentCategoryAdapter?.submitList(getDepartments())
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
            etSearch.addTextChangedListener { text ->
                val filterDoctors = mDoctors.filter { doctor ->
                    doctor.user?.fullName?.contains(
                        text.toString(),
                        true
                    ) == true || doctor.department?.name?.contains(text.toString(), true) ?: false
                }
                mDoctorAdapter?.submitList(filterDoctors)
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.searchDoctor(mapOf())
                swipeRefresh.isRefreshing = false
            }
        }
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
        }
        // clear argument
        arguments = null
    }

    private fun sortDoctors(sortType: SortType) {
        binding.tvSortType.text = sortType.value
        val params: MutableMap<String, Any> = HashMap()
        when (sortType) {
            SortType.NAME_AZ -> {
                params[Define.Fields.ORDER] = ORDER_ASC
                params[Define.Fields.ORDER_BY] = ORDER_BY_FULL_NAME
                viewModel.searchDoctor(params)
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            }

            SortType.NAME_ZA -> {
                params[Define.Fields.ORDER] = ORDER_DESC
                params[Define.Fields.ORDER_BY] = ORDER_BY_FULL_NAME
                viewModel.searchDoctor(params)
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }

            SortType.YOE_ASC -> {
                params[Define.Fields.ORDER] = ORDER_ASC
                params[Define.Fields.ORDER_BY] = ORDER_BY_YOE
                viewModel.searchDoctor(params)
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_up,
                    0
                )
            }

            SortType.YOE_DESC -> {
                params[Define.Fields.ORDER] = ORDER_DESC
                params[Define.Fields.ORDER_BY] = ORDER_BY_YOE
                viewModel.searchDoctor(params)
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_arrow_down,
                    0
                )
            }

            else -> {
                viewModel.searchDoctor(params)
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


    private fun scrollToTop() {
        //delay to scroll to top after list is updated
        binding.rvDoctor.postDelayed({
            binding.rvDoctor.smoothScrollToPosition(0)
        }, 100)

    }

    private fun getDepartments(): ArrayList<Department> {
        return arrayListOf(
            Department(
                "0",
                "All",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "1",
                "Cardiology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "2",
                "Neurology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "3",
                "Orthopedics",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "4",
                "Pediatrics",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "5",
                "Dermatology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "6",
                "Radiology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "7",
                "Oncology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                "8",
                "Gastroenterology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            )
        )
    }

    override fun onDepartmentClick(position: Int) {
        val selectedCategory = getDepartments()[position]
        if (selectedCategory.name == "All") {
            mDoctorAdapter?.submitList(mDoctors)
            binding.tvDoctorCount.text =
                String.format(getString(R.string.string_search_doctor_count), mDoctors.size)
            return
        }
        val filterDoctors = mDoctors.filter { it.department?.name == selectedCategory.name }
        mDoctorAdapter?.submitList(filterDoctors)
        binding.tvDoctorCount.text =
            String.format(getString(R.string.string_search_doctor_count), filterDoctors.size)

    }


}