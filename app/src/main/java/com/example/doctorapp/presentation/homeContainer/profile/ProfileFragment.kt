package com.example.doctorapp.presentation.homeContainer.profile

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentProfileBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.presentation.utils.Dialog

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {


    private val viewModel: ProfileViewModel by viewModels()
    override fun getVM() = viewModel

    private var mCapturedImageURI: Uri? = null

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
            // Handle the returned Uri
            try {
                Glide.with(requireContext())
                    .load(mCapturedImageURI)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.ivAvatar)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the returned Uri
        try {
            Glide.with(requireContext())
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivAvatar)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.ivAvatar.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                mediaPermission.launch(takePhotoCameraPermissionSDK33)
            } else {
                mediaPermission.launch(takePhotoCameraPermissions)
            }
        }
    }

    private fun requestPermissions() {
        //check the API level
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //filter permissions array in order to get permissions that have not been granted
            val notGrantedPermissions = takePhotoCameraPermissionSDK33.filterNot { permission ->
                context?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED
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
        } else {
            //check if permission is granted
            val notGrantedPermissions = takePhotoCameraPermissions.filterNot { permission ->
                context?.let { ContextCompat.checkSelfPermission(it, permission) } == PackageManager.PERMISSION_GRANTED
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
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, CAMERA_IMAGE_FILE_NAME)
        mCapturedImageURI =
            requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        cameraLauncher.launch(mCapturedImageURI)
    }

    companion object {
        private const val TAG = "ProfileFragment"
        private const val GALLERY_TYPE = "image/*"
        private const val CAMERA_IMAGE_FILE_NAME = "temp.jpg"

        fun newInstance() = ProfileFragment()
    }

}