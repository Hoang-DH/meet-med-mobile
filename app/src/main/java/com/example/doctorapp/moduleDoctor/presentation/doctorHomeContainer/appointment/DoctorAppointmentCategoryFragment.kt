package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.appointment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.DoctorAppointment
import com.example.doctorapp.data.model.TimeSlot
import com.example.doctorapp.databinding.FragmentDoctorAppointmentCategoryBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.moduleDoctor.presentation.adapter.DoctorAppointmentAdapter

class DoctorAppointmentCategoryFragment : BaseFragment<FragmentDoctorAppointmentCategoryBinding, DoctorAppointmentCategoryViewModel>(R.layout.fragment_doctor_appointment_category) {

    companion object {
        fun newInstance(category: String): DoctorAppointmentCategoryFragment {
            val fragment = DoctorAppointmentCategoryFragment()
            val bundle = Bundle()
            bundle.putString(Define.Fields.CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: DoctorAppointmentCategoryViewModel by viewModels()
    override fun getVM() = viewModel
    private var mAppointmentAdapter: DoctorAppointmentAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mAppointmentAdapter = DoctorAppointmentAdapter(requireContext())
        binding.apply {
            rvAppointment.adapter = mAppointmentAdapter
            rvAppointment.layoutManager = LinearLayoutManager(requireContext())
        }
        generateAppointmentList()
    }

    //create function to generate list of appointment
    private fun generateAppointmentList() {
        val appointmentList = mutableListOf<DoctorAppointment>()
        for (i in 1..10) {
            appointmentList.add(
                DoctorAppointment(
                    id = i.toString(),
                    patientName = "Patient $i",
                    phone = "0123456789$i",
                    email = "patient$i@example.com",
                    timeSlot = TimeSlot("1","09:00", "10:00"),
                    symtom = "Symptom $i"
                )
            )
        }
        mAppointmentAdapter?.submitList(appointmentList)
    }
}