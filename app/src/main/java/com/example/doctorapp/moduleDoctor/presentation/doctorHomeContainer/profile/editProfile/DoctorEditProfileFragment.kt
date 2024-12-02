package com.example.doctorapp.moduleDoctor.presentation.doctorHomeContainer.profile.editProfile

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.doctorapp.R
import com.example.doctorapp.data.model.Doctor
import com.example.doctorapp.data.model.User
import com.example.doctorapp.databinding.FragmentDoctorEditProfileBinding
import com.example.doctorapp.databinding.PopupGenderBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.constants.Gender
import com.example.doctorapp.modulePatient.presentation.navigation.AppNavigation
import com.example.doctorapp.utils.ApplicationMediaManager
import com.example.doctorapp.utils.DateTimePickerDialog
import com.example.doctorapp.utils.DateUtils
import com.example.doctorapp.utils.Dialog
import com.example.doctorapp.utils.MyResponse
import com.example.doctorapp.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorEditProfileFragment :
    BaseFragment<FragmentDoctorEditProfileBinding, DoctorEditProfileViewModel>(R.layout.fragment_doctor_edit_profile) {

    @Inject
    lateinit var appNavigation: AppNavigation


    private val viewModel: DoctorEditProfileViewModel by viewModels()
    override fun getVM() = viewModel
    private var avatarImageUri: Uri? = null
    private var popupMenu: PopupWindow? = null
    private var cloudinaryUrl: String? = null


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
                MediaManager.get().upload(avatarImageUri).callback(object : UploadCallback {
                    override fun onStart(requestId: String?) {
                        Log.d("HoangDH", "onStart")
                    }

                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                        Log.d("HoangDH", "onProgress")
                        showHideLoading(true)
                    }

                    override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                        Log.d("HoangDH", resultData.toString())
                        cloudinaryUrl = resultData?.get(CLOUDINARY_IMAGE_URL).toString()

                        showHideLoading(false)
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        Log.d("HoangDH", error.toString())
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                        Log.d("HoangDH", error.toString())
                    }

                }).dispatch()
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
            MediaManager.get().upload(avatarImageUri).callback(object : UploadCallback {
                override fun onStart(requestId: String?) {
                    Log.d("HoangDH", "onStart")
                    showHideLoading(true)
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    Log.d("HoangDH", "onProgress")
                    showHideLoading(true)
                }

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    Log.d("HoangDH", resultData.toString())
                    cloudinaryUrl = resultData?.get(CLOUDINARY_IMAGE_URL).toString()
                    showHideLoading(false)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    Log.d("HoangDH", error.toString())
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), "Error occurred, please try again!")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    Log.d("HoangDH", error.toString())
                    showHideLoading(false)
                    Dialog.showDialogError(requireContext(), "Error occurred, please try again!")
                }

            }).dispatch()
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
        cloudinaryUrl = Prefs.getInstance(requireContext()).doctor?.user?.imageUrl
        if (Prefs.getInstance(requireContext()).doctor != null) {
            val doctor = Prefs.getInstance(requireContext()).doctor
            binding.apply {

                etFullName.setText(doctor?.user?.fullName)
                etAge.setText(doctor?.user?.age.toString())
                etEmail.setText(doctor?.user?.email)
                etPhoneNumber.setText(doctor?.user?.phone)
                etPhoneNumber.setText(doctor?.user?.phone)
                etGender.text = doctor?.user?.gender?.value ?: "Gender"
                etGender.setTypeface(null, Typeface.NORMAL)
                etDescription.setText(doctor?.description)
                context?.let {
                    Glide.with(it)
                        .load(doctor?.user?.imageUrl)
                        .apply(
                            RequestOptions().placeholder(R.drawable.ic_profile_pic).error(R.drawable.ic_profile_pic)
                                .circleCrop()
                        )
                        .into(ivAvatar)
                }
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.doctorProfileResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    Prefs.getInstance(requireContext()).doctor = response.data
                    context?.let {
                        Dialog.showCongratulationDialog(
                            it,
                            getString(R.string.string_edit_profile_successfully),
                            onClickDone = {
                                appNavigation.navigateUp()
                            }
                        )
                    }
                }

                is MyResponse.Error -> {
                    showHideLoading(false)
                    context?.let {
                        Dialog.showDialogError(
                            it,
                            response.exception.message ?: "Error occurred, please try again!",
                            onClickOk = {
                                appNavigation.navigateUp()
                            }
                        )
                    }
                }
            }
        }
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
                    imageUrl = cloudinaryUrl
                )
                val doctor = Doctor(
                    user = user,
                    description = binding.etDescription.text.toString(),
                )
                viewModel.updateDoctorProfile(doctor)
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
                Toast.makeText(requireContext(), "Read media storage permission denied!", Toast.LENGTH_SHORT).show()
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
        private const val CLOUDINARY_IMAGE_URL = "secure_url"
        fun newInstance() = DoctorEditProfileFragment()
    }


}