package com.example.doctorapp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.utils.ApplicationMediaManager
import com.example.doctorapp.utils.Prefs
import com.example.doctorapp.utils.SocketHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.resume

class SendMessageWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    private var cloudinaryUrl: String? = null
    override suspend fun doWork(): Result {
        ApplicationMediaManager.startMediaManager(applicationContext)
        val messageContent = inputData.getString(MESSAGE_CONTENT) ?: return Result.failure()
        val to = inputData.getString(Define.Socket.TO) ?: return Result.failure()
        val chatBoxId = inputData.getString(Define.Socket.CHAT_BOX_ID) ?: return Result.failure()
        val messageType = inputData.getString(Define.Socket.TYPE) ?: return Result.failure()
        if (messageType == "TEXT") {
            return withContext(Dispatchers.IO) {
                val result = suspendCancellableCoroutine { continuation ->
                    val sendMessage = JSONObject()
                    val mContent = JSONObject()
                    var isResumed = false
                    fun safeResume(result: Result) {
                        if (!isResumed) {
                            isResumed = true
                            continuation.resume(result)
                        }
                    }
                    try {
                        sendMessage.put(Define.Socket.TO, to)
                        mContent.put(Define.Socket.CONTENT, messageContent)
                        mContent.put(Define.Socket.TYPE, messageType)
                        mContent.put(Define.Socket.CHAT_BOX_ID, chatBoxId)
                        sendMessage.put(Define.Socket.MESSAGE, mContent)
                        //check if socket is connected
                        if (SocketHandler.getSocket() == null) {
                            SocketHandler.initSocket(Prefs.getInstance(applicationContext).patient?.id ?: "")
                        }
                        if (SocketHandler.getSocket()?.connected() == false) {
                            SocketHandler.getSocket()?.connect()
                        }
                        SocketHandler.getSocket()?.on(Define.Socket.EVENT_MESSAGE_ACK) { args ->
                            val wData = workDataOf(
                                MESSAGE_SENT to args[0].toString()
                            )
                            Log.d("SocketHandler", args[0].toString())
                            safeResume(Result.success(wData))
                        }
                        SocketHandler.getSocket()?.emit(Define.Socket.EVENT_SEND_MESSAGE, sendMessage)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                return@withContext result
            }
        } else {
            return withContext(Dispatchers.IO) {
                try {
                    val result = suspendCancellableCoroutine { continuation ->
                        MediaManager.get().upload(messageContent)
                            .option("resource_type", messageType.lowercase())
                            .callback(object : UploadCallback {
                                override fun onStart(requestId: String?) {
                                    Log.d("HoangDH", "onStart")
                                    cloudinaryUrl = null
                                }

                                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                                    Log.d("HoangDH", "onProgress")
                                    cloudinaryUrl = null
                                }

                                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                                    Log.d("HoangDH", "success:" + resultData.toString())
                                    cloudinaryUrl = resultData?.get(CLOUDINARY_IMAGE_URL).toString()

                                    val sendMessage = JSONObject()
                                    val messageContent = JSONObject()
                                    try {
                                        sendMessage.put(Define.Socket.TO, to)
                                        messageContent.put(Define.Socket.CONTENT, cloudinaryUrl)
                                        messageContent.put(Define.Socket.TYPE, messageType)
                                        messageContent.put(Define.Socket.CHAT_BOX_ID, chatBoxId)
                                        sendMessage.put(Define.Socket.MESSAGE, messageContent)
                                        //check if socket is connected
                                        if (SocketHandler.getSocket() == null) {
                                            SocketHandler.initSocket(
                                                Prefs.getInstance(applicationContext).patient?.id ?: ""
                                            )
                                        }
                                        if (SocketHandler.getSocket()?.connected() == false) {
                                            SocketHandler.getSocket()?.connect()
                                        }
                                        SocketHandler.getSocket()?.on(Define.Socket.EVENT_MESSAGE_ACK) { args ->
                                            val wData = workDataOf(
                                                MESSAGE_SENT to args[0].toString()
                                            )
                                            Log.d("SocketHandler", args[0].toString())
                                            continuation.resume(Result.success(wData))
                                        }
                                        SocketHandler.getSocket()?.emit(Define.Socket.EVENT_SEND_MESSAGE, sendMessage)
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }

                                override fun onError(requestId: String?, error: ErrorInfo?) {
                                    Log.d("HoangDH", error.toString())
                                    cloudinaryUrl = null
                                    continuation.resume(Result.failure())
                                }

                                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                                    Log.d("HoangDH", error.toString())
                                    cloudinaryUrl = null
                                    continuation.resume(Result.retry())
                                }
                            }).dispatch()
                    }
                    if (SocketHandler.getSocket()?.connected() == true) {
                        SocketHandler.getSocket()?.apply {
                            off(Define.Socket.EVENT_MESSAGE_ACK)
                            disconnect()
                        }
                    }
                    return@withContext result
                } catch (e: Exception) {
                    return@withContext Result.failure()
                }
            }
        }


    }

    private fun sendMessage(message: Message) {

    }

    companion object {
        const val WORK_NAME = "UploadMediaWorker"
        const val MESSAGE_CONTENT = "message_content"
        const val CLOUDINARY_IMAGE_URL = "secure_url"
        const val MESSAGE_SENT = "message_sent"
    }
}