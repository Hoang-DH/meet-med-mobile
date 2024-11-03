package com.example.doctorapp.presentation.homeContainer.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.doctorapp.R
import com.example.doctorapp.constant.Constant
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.databinding.FragmentHomeBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.adapter.DepartmentAdapter
import com.example.doctorapp.presentation.adapter.SmallDoctorItemAdapter
import com.example.doctorapp.presentation.homeContainer.search.SearchDoctorViewModel
import com.example.doctorapp.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var appNavigation: AppNavigation
    private var mAdapter: DepartmentAdapter? = null
    private val mDoctorAdapter: SmallDoctorItemAdapter by lazy {
        SmallDoctorItemAdapter(requireContext()){
            val bundle = Bundle()
            bundle.putParcelable(Constant.BundleKey.DOCTOR, it)
            appNavigation.openHomeToDoctorDetailScreen(bundle)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private val searchViewModel: SearchDoctorViewModel by viewModels()
    override fun getVM() = viewModel


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        viewModel.getDoctors()
        binding.apply {
            mAdapter = DepartmentAdapter(requireContext())
            mAdapter?.submitList(getDepartments())
            rvDepartment.layoutManager =
                androidx.recyclerview.widget.GridLayoutManager(
                    requireContext(),
                    4
                )
            rvDepartment.adapter = mAdapter
            mDoctorAdapter.submitList(viewModel.doctorList.value)
            rvDoctor.adapter = mDoctorAdapter
            rvDoctor.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(
                    requireContext(),
                    androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                    false
                )
        }
    }

    override fun bindingAction() {
        super.bindingAction()
        binding.etSearch.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isClickable = true
            setOnClickListener {
                appNavigation.openHomeContainerToSearchDoctorScreen()
            }
        }
    }

    private fun getDepartments(): ArrayList<Department> {
        return arrayListOf(
            Department(
                1,
                "Cardiology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                2,
                "Neurology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                3,
                "Orthopedics",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                4,
                "Pediatrics",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                5,
                "Dermatology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                6,
                "Radiology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                7,
                "Oncology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
            Department(
                8,
                "Gastroenterology",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            )
        )
    }



}