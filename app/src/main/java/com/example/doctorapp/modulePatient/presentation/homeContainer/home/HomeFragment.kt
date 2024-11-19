package com.example.doctorapp.modulePatient.presentation.homeContainer.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.databinding.FragmentHomeBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.DepartmentAdapter
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    @Inject
    lateinit var appNavigation: AppNavigation
    private var mAdapter: DepartmentAdapter? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    override fun getVM() = viewModel


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mAdapter = DepartmentAdapter(requireContext())
        mAdapter?.submitList(getDepartments())
        binding.apply {
            rvDepartment.layoutManager =
                androidx.recyclerview.widget.GridLayoutManager(
                    requireContext(),
                    4
                )
            rvDepartment.adapter = mAdapter

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