package com.example.doctorapp.presentation.homeContainer.profile.editProfile

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentEditProfileBinding
import com.example.doctorapp.databinding.PopupGenderBinding
import com.example.doctorapp.presentation.constants.Gender
import com.example.doctorapp.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.Dialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(R.layout.fragment_edit_profile) {

        @Inject
        lateinit var appNavigation: AppNavigation

    companion object {
        fun newInstance() = EditProfileFragment()
    }

    private val viewModel: EditProfileViewModel by viewModels()
    override fun getVM() = viewModel


    private var popupMenu: PopupWindow? = null

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
            etGender.setOnClickListener {
                onGenderClick(it)
            }
            btnSave.setOnClickListener{
                context?.let{
                    Dialog.showCongratulationDialog(it, getString(R.string.string_edit_profile_successfully), true)
                    // after 3 seconds, navigate to home screen
                    binding.btnSave.postDelayed({
//                    appNavigation.openSignUpProfileToHomeContainerScreen()
                    }, 3000)

                }
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