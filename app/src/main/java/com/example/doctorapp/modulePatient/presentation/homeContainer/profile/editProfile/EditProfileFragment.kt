package com.example.doctorapp.modulePatient.presentation.homeContainer.profile.editProfile

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
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
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.circleCropTransform
import com.bumptech.glide.request.target.Target
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.doctorapp.R
import com.example.doctorapp.domain.model.Patient
import com.example.doctorapp.domain.model.User
import com.example.doctorapp.databinding.FragmentEditProfileBinding
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
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(R.layout.fragment_edit_profile), DateTimePickerDialog.OnDateTimePickerListener {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: EditProfileViewModel by viewModels()
    override fun getVM() = viewModel

    private var avatarImageUri: Uri? = null
    private var popupMenu: PopupWindow? = null
    private var cloudinaryUrl: String? = null

    private var takePhotoCameraPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
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
                    .apply(circleCropTransform())
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
                .apply(circleCropTransform())
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
                    Dialog.showDialogError( requireContext(), "Error occurred, please try again!" )
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    Log.d("HoangDH", error.toString())
                    showHideLoading(false)
                    Dialog.showDialogError( requireContext(), "Error occurred, please try again!" )
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
        cloudinaryUrl = Prefs.getInstance(requireContext()).patient?.user?.imageUrl
        if (Prefs.getInstance(requireContext()).patient != null) {
            val patient = Prefs.getInstance(requireContext()).patient
            binding.apply {
                etDob.apply {
                    isFocusable = false
                    isFocusableInTouchMode = false
                    isClickable = true
                    setText(patient?.dob)
                }
                etFullName.setText(patient?.user?.fullName)
                etAge.setText(patient?.user?.age.toString())
                etEmail.setText(patient?.user?.email)
                etPhoneNumber.setText(patient?.user?.phone)
                etDob.setText(patient?.dob)
                etPhoneNumber.setText(patient?.user?.phone)
                etAddressLine.setText(patient?.addressLine)
                etDistrict.setText(patient?.district)
                etCity.setText(patient?.city)
                etInsuranceCode.setText(patient?.insuranceCode)
                etGender.text = patient?.user?.gender?.value ?: "Gender"
                etGender.setTypeface(null, Typeface.NORMAL)
                context?.let {
                    progressBar.visibility = View.VISIBLE
                    Glide.with(it)
                        .load(if (cloudinaryUrl.isNullOrEmpty()) R.drawable.ic_profile_pic else cloudinaryUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: com.bumptech.glide.load.DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.visibility = View.GONE
                                return false
                            }
                        })
                        .placeholder(R.drawable.ic_profile_pic)
                        .apply(circleCropTransform())
                        .into(ivAvatar)
                }
            }
            DateTimePickerDialog.getInstance(DateUtils.convertDateToLong(binding.etDob.text.toString())).setOnDateTimePickerListener(this)
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.patientProfileResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is MyResponse.Loading -> {
                    showHideLoading(true)
                }

                is MyResponse.Success -> {
                    showHideLoading(false)
                    Prefs.getInstance(requireContext()).patient = response.data
                    context?.let {
                        if (Prefs.getInstance(requireContext()).isProfileExist) {
                            Dialog.showCongratulationDialog(
                                it,
                                getString(R.string.string_edit_profile_successfully),
                                onClickDone = {
                                    appNavigation.navigateUp()
                                }
                            )
                        } else {
                            Dialog.showCongratulationDialog(
                                it,
                                getString(
                                    R.string.string_create_profile_successfully
                                ),
                                //navigate to homescreen
                                onClickDone = {
                                    appNavigation.openEditProfileToHomeContainerScreen()
                                }
                            )
                        }

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
        binding.etDob.setOnClickListener {
            if(binding.etDob.text.isNullOrEmpty()) {
                DateTimePickerDialog.getInstance().showDatePickerDialog(childFragmentManager)
            } else {
                DateTimePickerDialog.getInstance(DateUtils.convertDateToLong(binding.etDob.text.toString())).showDatePickerDialog(childFragmentManager)
            }
        }
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
                    imageUrl = cloudinaryUrl,
                    )
                val patient = Patient(
                    addressLine = binding.etAddressLine.text.toString(),
                    district = binding.etDistrict.text.toString(),
                    city = binding.etCity.text.toString(),
                    insuranceCode = binding.etInsuranceCode.text.toString(),
                    user = user,
                    dob = binding.etDob.text.toString()
                )
                if (Prefs.getInstance(requireContext()).isProfileExist) {
                    viewModel.updateProfile(patient)
                } else {
                    viewModel.createProfile(patient)
                }
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

    override fun onDestroy() {
        super.onDestroy()
        //remove listener
        MediaManager.get().cancelAllRequests()
        DateTimePickerDialog.getInstance().removeOnDateTimePickerListener()
    }

    companion object {
        private const val GALLERY_TYPE = "image/*"
        private const val CAMERA_IMAGE_FILE_NAME = "temp.jpg"
        private const val CLOUDINARY_IMAGE_URL = "secure_url"
        fun newInstance() = EditProfileFragment()
    }

    override fun onDateTimeSelected(date: Long) {
        binding.etDob.setText(DateUtils.convertLongToDate(date))
    }

}