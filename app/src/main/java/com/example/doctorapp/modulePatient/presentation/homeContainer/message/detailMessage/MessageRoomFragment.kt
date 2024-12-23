package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import com.example.doctorapp.R
import com.example.doctorapp.databinding.FragmentMessageRoomBinding
import com.example.doctorapp.domain.core.base.BaseFragment


class MessageRoomFragment :
    BaseFragment<FragmentMessageRoomBinding, MessageRoomViewModel>(R.layout.fragment_message_room) {

    private val viewModel: MessageRoomViewModel by viewModels()
    override fun getVM(): MessageRoomViewModel = viewModel

    private var selectedFile: Uri? = null;
    private var currentMediaPickType = IMAGE_TYPE

    private var takePhotoCameraPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val takePhotoCameraPermissionsSDK33 = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.CAMERA
    )

    private val imagePermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                openCamera(currentMediaPickType)
            } else {
                //showToast("Permission Denied")
            }

        }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            try {
                Log.d("Hoangdh", "imageLauncher")
//                Glide.with(requireContext())
//                    .load(avatarImageUri)
//                    .apply(circleCropTransform())
//                    .into(binding.ivAvatar)
////                MediaManager.get().upload(avatarImageUri).callback(object : UploadCallback {
////                    override fun onStart(requestId: String?) {
////                        Log.d("HoangDH", "onStart")
////                    }
////
////                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
////                        Log.d("HoangDH", "onProgress")
////                        showHideLoading(true)
////                    }
////
////                    override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
////                        Log.d("HoangDH", resultData.toString())
////                        cloudinaryUrl = resultData?.get(CLOUDINARY_IMAGE_URL).toString()
////
////                        showHideLoading(false)
////                    }
////
////                    override fun onError(requestId: String?, error: ErrorInfo?) {
////                        Log.d("HoangDH", error.toString())
////                    }
////
////                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {
////                        Log.d("HoangDH", error.toString())
////                    }
////
////                }).dispatch()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private val videoLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()) {
        if (it) {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val fileLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            try {
                selectedFile = it
                //uploadFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun openCamera(pickType: Int) {
        val values = ContentValues()
        if (pickType == IMAGE_TYPE) {
            values.put(MediaStore.Images.Media.TITLE, CAMERA_IMAGE_FILE_NAME)
            selectedFile =
                requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageLauncher.launch(selectedFile)
        } else {
            values.put(MediaStore.Video.Media.TITLE, CAMERA_VIDEO_FILE_NAME)
            selectedFile =
                requireContext().contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
            videoLauncher.launch(selectedFile)
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivAddImage.setOnClickListener {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    imagePermission.launch(takePhotoCameraPermissionsSDK33)
                } else {
                    imagePermission.launch(takePhotoCameraPermissions)
                }
                showMediaPickerLayout(binding.addMediaLayout.visibility == View.GONE)
            }
            tvTakePicture.setOnClickListener {
                currentMediaPickType = IMAGE_TYPE
                openCamera(currentMediaPickType)
            }
            tvRecordVideo.setOnClickListener {
                currentMediaPickType = VIDEO_TYPE
                openCamera(currentMediaPickType)
            }
        }
    }

    private fun showMediaPickerLayout(isShow: Boolean) {
        binding.apply {
            addMediaLayout.visibility = if (isShow) View.VISIBLE else View.GONE

            if (isShow) {
                addMediaLayout.doOnLayout {
                    rvMessageList.smoothScrollBy(0, addMediaLayout.height)
                }
            }
        }
    }

    companion object {
        private const val GALLERY_TYPE = "image/*"
        private const val CAMERA_IMAGE_FILE_NAME = "temp.jpg"
        private const val CAMERA_VIDEO_FILE_NAME = "temp.mp4"
        private const val IMAGE_TYPE = 0
        private const val VIDEO_TYPE = 0
        fun newInstance() = MessageRoomFragment()
    }
}