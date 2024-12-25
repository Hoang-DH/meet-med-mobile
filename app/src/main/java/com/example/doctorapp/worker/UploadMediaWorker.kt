package com.example.doctorapp.worker

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.doctorapp.constant.Define
import com.example.doctorapp.data.model.Message
import com.example.doctorapp.utils.ApplicationMediaManager
import com.example.doctorapp.utils.SocketHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.resume

class UploadMediaWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    private var cloudinaryUrl: String? = null
    override suspend fun doWork(): Result {
        ApplicationMediaManager.startMediaManager(applicationContext)
        val mediaString = inputData.getString(MEDIA_URI) ?: return Result.failure()
        val to = inputData.getString(Define.Socket.TO) ?: return Result.failure()
        val chatBoxId = inputData.getString(Define.Socket.CHAT_BOX_ID) ?: return Result.failure()
        val messageType = inputData.getString(Define.Socket.TYPE) ?: return Result.failure()
        val mediaUri = mediaString.toUri()
        return withContext(Dispatchers.IO) {
            try {
                val result = suspendCancellableCoroutine { continuation ->
                    MediaManager.get().upload(mediaUri).callback(object : UploadCallback {
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
                            continuation.resume(Result.success(workDataOf(CLOUDINARY_IMAGE_URL to cloudinaryUrl)))
                            val sendMessage = JSONObject()
                            val messageContent = JSONObject()
                            try {
                                sendMessage.put(Define.Socket.TO, to)
                                messageContent.put(Define.Socket.CONTENT, cloudinaryUrl)
                                messageContent.put(Define.Socket.TYPE, messageType)
                                messageContent.put(Define.Socket.CHAT_BOX_ID, chatBoxId)
                                sendMessage.put(Define.Socket.MESSAGE, messageContent)
                                //check if socket is connected
                                if(!SocketHandler.getSocket().connected()){
                                    SocketHandler.getSocket().connect()
                                }
                                SocketHandler.getSocket().emit(Define.Socket.EVENT_SEND_MESSAGE, sendMessage)
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
                            continuation.resume(Result.failure())
                        }
                    }).dispatch()
                }
                if(SocketHandler.getSocket().connected()){
                    SocketHandler.getSocket().disconnect()
                }
                return@withContext result
            } catch (e: Exception) {
                return@withContext Result.failure()
            }
        }

    }

    private fun sendMessage(message: Message) {

    }

    companion object {
        const val WORK_NAME = "UploadMediaWorker"
        const val MEDIA_URI = "media_uri"
        const val CLOUDINARY_IMAGE_URL = "secure_url"
    }
}