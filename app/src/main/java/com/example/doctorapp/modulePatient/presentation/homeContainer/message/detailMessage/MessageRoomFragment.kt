package com.example.doctorapp.modulePatient.presentation.homeContainer.message.detailMessage

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout
import androidx.fragment.app.viewModels
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.doctorapp.R
import com.example.doctorapp.constant.Define
import com.example.doctorapp.constant.MessageStatus
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.databinding.FragmentMessageRoomBinding
import com.example.doctorapp.domain.core.base.BaseFragment
import com.example.doctorapp.modulePatient.presentation.adapter.MessageAdapter
import com.example.doctorapp.utils.Prefs
import com.example.doctorapp.utils.SocketHandler
import com.example.doctorapp.worker.UploadMediaWorker
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class MessageRoomFragment :
    BaseFragment<FragmentMessageRoomBinding, MessageRoomViewModel>(R.layout.fragment_message_room) {

    private val viewModel: MessageRoomViewModel by viewModels()
    override fun getVM(): MessageRoomViewModel = viewModel

    private var selectedFile: Uri? = null;
    private var currentMediaPickType = IMAGE_TYPE
    private var messageAdapter: MessageAdapter? = null


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
                showMediaPickerLayout(binding.addMediaLayout.visibility == View.GONE)
            } else {
                //showToast("Permission Denied")
            }

        }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            try {
                Log.d("Hoangdh", selectedFile.toString())
                val inputData = Data.Builder().putString("mediaUri", selectedFile.toString()).build()
                val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                val uploadImageRequest = OneTimeWorkRequest.Builder(UploadMediaWorker::class.java)
                    .setInputData(inputData)
                    .setConstraints(constraints)
                    .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(requireContext()).enqueue(uploadImageRequest)
                WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(uploadImageRequest.id).observe(viewLifecycleOwner) { workInfo ->
                    if (workInfo != null) {
                        when (workInfo.state) {
                            WorkInfo.State.SUCCEEDED -> {
                                val message = Message(
                                    id = "1",
                                    messageContent = workInfo.outputData.getString(UploadMediaWorker.CLOUDINARY_IMAGE_URL).toString(),
                                    patient = Prefs.getInstance(requireContext()).patient,
                                    type = "IMAGE",
                                    status = MessageStatus.SENT
                                )
                                sendMessage(message)
                                viewModel.updateMessageStatus(message, MessageStatus.SENT)
                            }
                            WorkInfo.State.FAILED -> {

                            }
                            WorkInfo.State.RUNNING -> {
                                // Image upload is in progress
                                val message = Message(
                                    id = "1",
                                    messageContent = workInfo.outputData.getString(UploadMediaWorker.CLOUDINARY_IMAGE_URL).toString(),
                                    patient = Prefs.getInstance(requireContext()).patient,
                                    type = "IMAGE",
                                    status = MessageStatus.SENDING
                                )
                                viewModel.sendMessage(message)
                                showHideLoading(true)
                            }
                            else -> {
                                // Handle other states (e.g., PENDING, CANCELLED)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val videoLauncher = registerForActivityResult(ActivityResultContracts.CaptureVideo()) {
        if (it) {
            try {
                // Handle the recorded video URI
                Log.d("MessageRoomFragment", "Video recorded")
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
        if (pickType == IMAGE_TYPE) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, CAMERA_IMAGE_FILE_NAME)
            selectedFile =
                requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageLauncher.launch(selectedFile)
        } else {
            val values = ContentValues()
            values.put(MediaStore.Video.Media.TITLE, CAMERA_VIDEO_FILE_NAME)
            selectedFile =
                requireContext().contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
            videoLauncher.launch(selectedFile)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.getInstance(requireContext()).patient?.id?.let { SocketHandler.initSocket(it) }
        Log.d("SocketHandler", Prefs.getInstance(requireContext()).patient?.id.toString())
        SocketHandler.getSocket().apply {
            on(Define.Socket.EVENT_RECEIVE_MESSAGE, onReceiveMessage)
            on(Define.Socket.EVENT_ERROR, onError)
            on(Define.Socket.EVENT_CONNECTED) {
                Log.d("SocketHandler", "Connected")
            }
            on(Define.Socket.EVENT_MESSAGE_ACK, onMessageAck)
            connect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.getSocket().apply {
            off(Define.Socket.EVENT_RECEIVE_MESSAGE, onReceiveMessage)
            off(Define.Socket.EVENT_ERROR, onError)
            off(Define.Socket.EVENT_CONNECTED){
                Log.d("SocketHandler", "Disconnected")
            }
            off(Define.Socket.EVENT_MESSAGE_ACK, onMessageAck)
            disconnect()
            Log.d("SocketHandler", "Destroy")
        }
    }



    private val onReceiveMessage = Emitter.Listener { args: Array<out Any>? ->
        // Handle receive message
        Log.d("SocketHandler", "Receive message: ${args?.forEach { Log.d("SocketHandler", it.toString()) }}")
    }

    private val onError = Emitter.Listener { args: Array<out Any>? ->
        // Handle error
        Log.d("SocketHandler", "Error: ${args?.get(0)}")
    }

    private val onMessageAck = Emitter.Listener { args: Array<out Any>? ->
        // Handle message ack
        Log.d("SocketHandler", "Message ack: ${args?.forEach { Log.d("SocketHandler", it.toString()) }}")
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        messageAdapter = MessageAdapter(requireContext())
        binding.apply {
            rvMessageList.adapter = messageAdapter
            rvMessageList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }

    }


    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.messageList.observe(viewLifecycleOwner) {
            messageAdapter?.submitList(it)
            messageAdapter?.notifyItemInserted(it.size)
        }


    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            ivAddImage.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    imagePermission.launch(takePhotoCameraPermissionsSDK33)
                } else {
                    imagePermission.launch(takePhotoCameraPermissions)
                }
            }
            tvTakePicture.setOnClickListener {
                currentMediaPickType = IMAGE_TYPE
                openCamera(currentMediaPickType)
            }
            tvRecordVideo.setOnClickListener {
                currentMediaPickType = VIDEO_TYPE
                openCamera(currentMediaPickType)
            }

            ivSendMessage.setOnClickListener {
                val message = Message(
                    id = Random(100).nextInt().toString(),
                    messageContent = binding.etInputMessage.text.toString(),
                    patient = Prefs.getInstance(requireContext()).patient,
                    type = "TEXT",
                    status = MessageStatus.SENT
                )
                binding.etInputMessage.text?.clear()
                sendMessage(message)
                viewModel.sendMessage(message)
            }
        }
    }

    private fun sendMessage(message: Message) {
        val sendMessage = JSONObject()
        val messageContent = JSONObject()
        try{
            sendMessage.put(Define.Socket.TO, TO_ID)
            messageContent.put(Define.Socket.CONTENT, message.messageContent)
            messageContent.put(Define.Socket.TYPE, message.type)
            messageContent.put(Define.Socket.CHAT_BOX_ID, CHAT_BOX_ID)
            sendMessage.put(Define.Socket.MESSAGE, messageContent)
        } catch (e: JSONException){
            e.printStackTrace()
        }
        SocketHandler.getSocket().emit(Define.Socket.EVENT_SEND_MESSAGE, sendMessage)
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
        private const val VIDEO_TYPE = 1
        private const val CHAT_BOX_ID = "29764836-c00a-415b-b8a0-cdcdde53b16d"
        private const val TO_ID = "6a5c5552-aaba-485e-9382-e0cffddcc3f4"
        fun newInstance() = MessageRoomFragment()
    }
}