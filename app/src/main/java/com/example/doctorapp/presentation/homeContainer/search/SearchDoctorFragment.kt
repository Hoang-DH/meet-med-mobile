package com.example.doctorapp.presentation.homeContainer.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.SortType
import com.example.doctorapp.data.model.Department
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.databinding.FragmentSearchDoctorBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.adapter.DepartmentCategoryAdapter
import com.example.doctorapp.presentation.adapter.SearchDoctorAdapter
import com.example.doctorapp.presentation.navigation.AppNavigation

import com.example.doctorapp.utils.Define
import com.example.doctorapp.utils.Dialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchDoctorFragment :
    BaseFragment<FragmentSearchDoctorBinding, SearchDoctorViewModel>(R.layout.fragment_search_doctor) {

    @Inject
    lateinit var appNavigation: AppNavigation
    private var mDoctorAdapter: SearchDoctorAdapter? = null
    private var mDepartmentCategoryAdapter: DepartmentCategoryAdapter? = null

    companion object {
        fun newInstance() = SearchDoctorFragment()
    }

    private val viewModel: SearchDoctorViewModel by viewModels()
    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDoctorAdapter = SearchDoctorAdapter(requireContext()) { doctor ->
            val bundle = Bundle()
            bundle.putParcelable(Define.BundleKey.DOCTOR, doctor)
            appNavigation.openSearchDoctorToDoctorDetailScreen(bundle)
        }
        mDepartmentCategoryAdapter = DepartmentCategoryAdapter(requireContext()) { position ->
            val selectedCategory = getDepartments()[position]
            if (selectedCategory.name == "All") {
                mDoctorAdapter?.submitList(getDoctors())
                binding.tvDoctorCount.text =
                    String.format(getString(R.string.string_search_doctor_count), getDoctors().size)
                return@DepartmentCategoryAdapter
            }
            val filterDoctors = getDoctors().filter { it.speciality == selectedCategory.name }
            mDoctorAdapter?.submitList(filterDoctors)
            binding.tvDoctorCount.text =
                String.format(getString(R.string.string_search_doctor_count), filterDoctors.size)
        }
        mDepartmentCategoryAdapter?.submitList(getDepartments())
        mDoctorAdapter?.submitList(getDoctors())
        binding.apply {
            rvDoctor.apply {
                adapter = mDoctorAdapter
                itemAnimator = null
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            rvCategory.apply {
                adapter = mDepartmentCategoryAdapter
                itemAnimator = null
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            tvDoctorCount.text = String.format(getString(R.string.string_search_doctor_count), getDoctors().size)
            etSearch.addTextChangedListener { text ->
                val filterDoctors = getDoctors().filter { doctor ->
                    doctor.name.contains(text.toString(), true) ||
                            doctor.speciality.contains(text.toString(), true)
                }
                mDoctorAdapter?.submitList(filterDoctors)
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
                    onClickSortStarAsc = {
                        sortDoctors(SortType.STAR_ASC)
                    }, onClickSortStarDes = {
                        sortDoctors(SortType.STAR_DESC)
                    }, onClickSortReviewAsc = {
                        sortDoctors(SortType.REVIEW_ASC)
                    }, onClickSortReviewDes = {
                        sortDoctors(SortType.REVIEW_DESC)
                    },
                    view = tvSortType
                )
            }
            ivBack.setOnClickListener {
                appNavigation.openSearchDoctorToHomeContainerScreen()
            }
        }
    }

    private fun sortDoctors(sortType: SortType) {
        binding.tvSortType.text = sortType.value
        when (sortType) {
            SortType.STAR_ASC -> {
                mDoctorAdapter?.submitList(getDoctors().sortedBy { it.rating })
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
            }

            SortType.STAR_DESC -> {
                mDoctorAdapter?.submitList(getDoctors().sortedByDescending { it.rating })
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            }

            SortType.REVIEW_ASC -> {
                mDoctorAdapter?.submitList(getDoctors().sortedBy { it.reviewCount })
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
            }

            SortType.REVIEW_DESC -> {
                mDoctorAdapter?.submitList(getDoctors().sortedByDescending { it.reviewCount })
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            }

            else -> {
                mDoctorAdapter?.submitList(getDoctors())
                binding.tvSortType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_default, 0)
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


    private fun getDoctors(): List<Doctor> {
        return listOf(
            Doctor(
                1,
                "Dr. John Doe",
                "Cardiology",
                "Expert in heart diseases",
                "123-456-7890",
                "john.doe@example.com",
                4.5f,
                120,
                50,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                10
            ),
            Doctor(
                2,
                "Dr. Jane Smith",
                "Neurology",
                "Specialist in brain disorders",
                "123-456-7891",
                "jane.smith@example.com",
                4.7f,
                98,
                60,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                12
            ),
            Doctor(
                3,
                "Dr. Emily Johnson",
                "Orthopedics",
                "Orthopedic surgeon",
                "123-456-7892",
                "emily.johnson@example.com",
                4.6f,
                110,
                45,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                8
            ),
            Doctor(
                4,
                "Dr. Michael Brown",
                "Pediatrics",
                "Child specialist",
                "123-456-7893",
                "michael.brown@example.com",
                4.8f,
                150,
                70,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                15
            ),
            Doctor(
                5,
                "Dr. Sarah Davis",
                "Dermatology",
                "Skin care expert",
                "123-456-7894",
                "sarah.davis@example.com",
                4.4f,
                85,
                30,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                7
            ),
            Doctor(
                6,
                "Dr. David Wilson",
                "Radiology",
                "Radiologist",
                "123-456-7895",
                "david.wilson@example.com",
                4.3f,
                75,
                25,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                9
            ),
            Doctor(
                7,
                "Dr. Laura Martinez",
                "Oncology",
                "Cancer specialist",
                "123-456-7896",
                "laura.martinez@example.com",
                4.9f,
                200,
                100,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                20
            ),
            Doctor(
                8,
                "Dr. James Anderson",
                "Gastroenterology",
                "Digestive system expert",
                "123-456-7897",
                "james.anderson@example.com",
                4.2f,
                65,
                20,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                11
            ),
            Doctor(
                9,
                "Dr. Linda Thomas",
                "Cardiology",
                "Heart surgeon",
                "123-456-7898",
                "linda.thomas@example.com",
                4.5f,
                130,
                55,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                14
            ),
            Doctor(
                10,
                "Dr. Robert Jackson",
                "Neurology",
                "Neurosurgeon",
                "123-456-7899",
                "robert.jackson@example.com",
                4.7f,
                105,
                40,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                13
            ),
            Doctor(
                11,
                "Dr. Patricia White",
                "Orthopedics",
                "Bone specialist",
                "123-456-7800",
                "patricia.white@example.com",
                4.6f,
                115,
                50,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                10
            ),
            Doctor(
                12,
                "Dr. Charles Harris",
                "Pediatrics",
                "Pediatrician",
                "123-456-7801",
                "charles.harris@example.com",
                4.8f,
                140,
                65,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                16
            ),
            Doctor(
                13,
                "Dr. Barbara Clark",
                "Dermatology",
                "Dermatologist",
                "123-456-7802",
                "barbara.clark@example.com",
                4.4f,
                90,
                35,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                6
            ),
            Doctor(
                14,
                "Dr. Christopher Lewis",
                "Radiology",
                "Radiologist",
                "123-456-7803",
                "christopher.lewis@example.com",
                4.3f,
                80,
                30,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                8
            ),
            Doctor(
                15,
                "Dr. Jennifer Robinson",
                "Oncology",
                "Oncologist",
                "123-456-7804",
                "jennifer.robinson@example.com",
                4.9f,
                190,
                90,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                18
            ),
            Doctor(
                16,
                "Dr. Daniel Walker",
                "Gastroenterology",
                "Gastroenterologist",
                "123-456-7805",
                "daniel.walker@example.com",
                4.2f,
                70,
                25,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                9
            ),
            Doctor(
                17,
                "Dr. Susan Hall",
                "Cardiology",
                "Cardiologist",
                "123-456-7806",
                "susan.hall@example.com",
                4.5f,
                125,
                50,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                12
            ),
            Doctor(
                18,
                "Dr. Paul Young",
                "Neurology",
                "Neurologist",
                "123-456-7807",
                "paul.young@example.com",
                4.7f,
                100,
                45,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                11
            ),
            Doctor(
                19,
                "Dr. Karen King",
                "Orthopedics",
                "Orthopedic surgeon",
                "123-456-7808",
                "karen.king@example.com",
                4.6f,
                120,
                55,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                10
            ),
            Doctor(
                20,
                "Dr. Mark Wright",
                "Pediatrics",
                "Pediatrician",
                "123-456-7809",
                "mark.wright@example.com",
                4.8f,
                135,
                60,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s",
                14
            )
        )
    }

    private fun getDepartments(): ArrayList<Department> {
        return arrayListOf(
            Department(
                0,
                "All",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6WwgH7Nl5_AW9nDCnR2Ozb_AU3rkIbSJdAg&s"
            ),
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