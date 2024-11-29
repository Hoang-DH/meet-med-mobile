package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.profile.editProfile

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.Patient
import com.example.doctorapp.data.model.User
import com.example.doctorapp.databinding.FragmentDoctorEditProfileBinding
import com.example.doctorapp.databinding.PopupGenderBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.example.doctorapp.modulePatient.presentation.homeContainer.profile.editProfile.EditProfileFragment
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.ApplicationMediaManager
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorEditProfileFragment : BaseFragment<FragmentDoctorEditProfileBinding, DoctorEditProfileViewModel>(R.layout.fragment_doctor_edit_profile) {

    @Inject
    lateinit var appNavigation: AppNavigation


    private val viewModel: DoctorEditProfileViewModel by viewModels()
    override fun getVM() = viewModel
    private var avatarImageUri: Uri? = null
    private var popupMenu: PopupWindow? = null

    private var takePhotoCameraPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val takePhotoCameraPermissionSDK33 = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.CAMERA
    )

    private val mediaPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionMap ->
            if (permissionMap.all { it.value }) {
                // open dialog to choose image
                Dialog.showChooseImageDialog(
                    requireContext(),
                    onClickCamera = {
                        // open camera
                        openCamera()
                    },
                    onClickGallery = {
                        // open gallery
                        galleryLauncher.launch(GALLERY_TYPE)
                    }
                )
            } else {
                // show dialog to explain why permission is needed
                requestPermissions()
            }
        }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            try {
                Glide.with(requireContext())
                    .load(avatarImageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivAvatar)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        avatarImageUri = uri
        try {
            Glide.with(requireContext())
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivAvatar)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // check if MediaManager is initialized
        ApplicationMediaManager.startMediaManager(requireContext())

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        if (Prefs.getInstance(requireContext()).patient != null) {
            val patient = Prefs.getInstance(requireContext()).patient
            binding.apply {
                etFullName.setText(patient?.user?.fullName)
                etAge.setText(patient?.user?.age.toString())
                etEmail.setText(patient?.user?.email)
                etPhoneNumber.setText(patient?.user?.phone)
                etDob.setText(patient?.user?.dob)
                etPhoneNumber.setText(patient?.user?.phone)
                etAddressLine.setText(patient?.addressLine)
                etDistrict.setText(patient?.district)
                etCity.setText(patient?.city)
                etGender.text = patient?.user?.gender?.value ?: "Gender"
                etGender.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
//        viewModel.patientProfileResponse.observe(viewLifecycleOwner) { response ->
//            when (response) {
//                is MyResponse.Loading -> {
//                    showHideLoading(true)
//                }
//
//                is MyResponse.Success -> {
//                    showHideLoading(false)
//                    context?.let {
//                        val dialog = Dialog.showCongratulationDialog(
//                            it,
//                            getString(R.string.string_edit_profile_successfully),
//                            true
//                        )
//                        // after 3 seconds, navigate to home screen
//                        Handler().postDelayed({
//                            if (Prefs.getInstance(requireContext()).isProfileExist) {
//                                appNavigation.navigateUp()
//                            } else {
//                                appNavigation.openEditProfileToHomeContainerScreen()
//                            }
//                            dialog.dismiss()
//
//                        }, 2000)
//
//
//                    }
//                }
//
//                is MyResponse.Error -> {
//                    showHideLoading(false)
//                    context?.let {
//                        Dialog.showDialogError(
//                            it,
//                            response.exception.message ?: "Error occurred, please try again!",
//                            onClickOk = {
//                                appNavigation.navigateUp()
//                            }
//                        )
//                    }
//                }
//            }
//        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivAvatar.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mediaPermission.launch(takePhotoCameraPermissionSDK33)
                } else {
                    mediaPermission.launch(takePhotoCameraPermissions)
                }
            }
            ivBack.setOnClickListener {
                appNavigation.navigateUp()
            }
            etGender.setOnClickListener {
                onGenderClick(it)
            }
            btnSave.setOnClickListener {
                val user = User(
                    fullName = binding.etFullName.text.toString(),
                    email = binding.etEmail.text.toString(),
                    age = binding.etAge.text.toString().toInt(),
                    phone = binding.etPhoneNumber.text.toString(),
                    gender = Gender.fromString(binding.etGender.text.toString()),
                )
                val doctor = Doctor(
                    user = user
                )

//                if (Prefs.getInstance(requireContext()).isProfileExist) {
//                    viewModel.updateProfile(patient)
//                } else {
//                    viewModel.createProfile(patient)
//                }
//                MediaManager.get().upload(avatarImageUri).callback(object : UploadCallback {
//                    override fun onStart(requestId: String?) {
//                        Log.d("HoangDH", "onStart")
//                    }
//
//                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
//                        Log.d("HoangDH", "onProgress")
//                    }
//
//                    override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
//                        Log.d("HoangDH", resultData.toString())
//                        context?.let {
//                            Dialog.showCongratulationDialog(
//                                it,
//                                getString(R.string.string_edit_profile_successfully),
//                                true
//                            )
//                            // after 3 seconds, navigate to home screen
//                            binding.btnSave.postDelayed({
//                                appNavigation.openEditProfileToHomeContainerScreen()
//                            }, 3000)
//                        }
//                    }
//
//                    override fun onError(requestId: String?, error: ErrorInfo?) {
//                        Log.d("HoangDH", error.toString())
//                    }
//
//                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {
//                        Log.d("HoangDH", error.toString())
//                    }
//
//                }).dispatch()
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, CAMERA_IMAGE_FILE_NAME)
        avatarImageUri =
            requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        cameraLauncher.launch(avatarImageUri)
    }

    private fun requestPermissions() {
        //check the API level
        val notGrantedPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //filter permissions array in order to get permissions that have not been granted
            takePhotoCameraPermissionSDK33.filterNot { permission ->
                context?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED
            }

        } else {
            //check if permission is granted
            takePhotoCameraPermissions.filterNot { permission ->
                context?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED
            }
        }
        if (notGrantedPermissions.isNotEmpty()) {
            //check if permission was previously denied and return a boolean value
            val showRationale = notGrantedPermissions.any { permission ->
                shouldShowRequestPermissionRationale(permission)
            }
            //if true, explain to user why granting this permission is important
            if (showRationale) {
                Dialog.showAlertDialog(
                    requireContext(),
                    "Storage Permission",
                    "Storage permission is needed in order to show images and videos",
                    onClickOk = {
                        mediaPermission.launch(notGrantedPermissions.toTypedArray())
                    },
                    onClickCancel = {
                        Toast.makeText(
                            requireContext(),
                            "Read media storage permission denied!",
                            Toast.LENGTH_SHORT
                        ).show()
                    })

            } else {
                //launch the videoPermission ActivityResultContract
                mediaPermission.launch(notGrantedPermissions.toTypedArray())
            }
        } else {
            Toast.makeText(requireContext(), "Read media storage permission granted", Toast.LENGTH_SHORT).show()
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
        popupGenderBinding.llMale.setOnClickListener {
            dismissPopup()
            binding.etGender.apply {
                text = Gender.MALE.value
                setTextColor(resources.getColor(R.color.black, null))
                setTypeface(null, Typeface.NORMAL)
            }
        }
        popupGenderBinding.llFemale.setOnClickListener {
            dismissPopup()
            binding.etGender.apply {
                text = Gender.FEMALE.value
                setTextColor(resources.getColor(R.color.black, null))
                setTypeface(null, Typeface.NORMAL)
            }

        }
        popupGenderBinding.llOther.setOnClickListener {
            dismissPopup()
            binding.etGender.apply {
                text = Gender.OTHER.value
                setTextColor(resources.getColor(R.color.black, null))
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

    companion object {
        private const val GALLERY_TYPE = "image/*"
        private const val CAMERA_IMAGE_FILE_NAME = "temp.jpg"
        fun newInstance() = DoctorEditProfileFragment()
    }

}