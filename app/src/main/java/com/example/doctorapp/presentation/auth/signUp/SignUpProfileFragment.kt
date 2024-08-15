package com.example.doctorapp.presentation.auth.signUp

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.example.chatapp.domain.core.base.BaseFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentSignUpProfileBinding
import com.example.doctorapp.databinding.PopupGenderBinding
import com.example.doctorapp.presentation.constants.Gender
import com.example.doctorapp.utils.Dialog

class SignUpProfileFragment :
    BaseFragment<FragmentSignUpProfileBinding, SignUpProfileViewModel>(R.layout.fragment_sign_up_profile) {

    companion object {
        fun newInstance() = SignUpProfileFragment()
    }

    private val viewModel: SignUpProfileViewModel by viewModels()
    override fun getVM() = viewModel


    private var popupMenu: PopupWindow? = null


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

    }

    override fun setOnClick() {
        super.setOnClick()
        binding.etGender.setOnClickListener {
            onGenderClick(it)
        }
        binding.btnSave.setOnClickListener{
            context?.let{
                Dialog.showCongratulationDialog(it, getString(R.string.string_account_ready), true)
            }
        }
    }

    private fun onGenderClick(view: View) {
        fun dismissPopup() {
            if (popupMenu?.isShowing == true) {
                popupMenu?.dismiss()
            }
        }

        val inflater = LayoutInflater.from(view.context)
        val popupGenderBinding = DataBindingUtil.inflate<PopupGenderBinding>(
            inflater,
            R.layout.popup_gender,
            null,
            false
        )
        binding.etGender.isAllCaps = false
        popupGenderBinding.llMale.setOnClickListener {
            dismissPopup()
            binding.etGender.apply {
                text = Gender.MALE.value
                setTextColor(resources.getColor(R.color.color_text_hint, null))
                setTypeface(null, Typeface.NORMAL)
            }
        }
        popupGenderBinding.llFemale.setOnClickListener {
            dismissPopup()
            binding.etGender.apply {
                text = Gender.FEMALE.value
                setTextColor(resources.getColor(R.color.color_text_hint, null))
                setTypeface(null, Typeface.NORMAL)
            }

        }
        popupGenderBinding.llOther.setOnClickListener {
            dismissPopup()
            binding.etGender.apply {
                text = Gender.OTHER.value
                setTextColor(resources.getColor(R.color.color_text_hint, null))
                setTypeface(null, Typeface.NORMAL)
            }
        }
        dismissPopup()
        popupMenu = PopupWindow(
            popupGenderBinding.root,
            view.width,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isOutsideTouchable = true
            isFocusable = true

        }
        popupMenu?.showAsDropDown(view, 0, -12)
    }
}