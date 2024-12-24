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
import com.example.doctorapp.utils.ApplicationMediaManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UploadMediaWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    private var cloudinaryUrl: String? = null
    override suspend fun doWork(): Result {
        ApplicationMediaManager.startMediaManager(applicationContext)
        val mediaString = inputData.getString(MEDIA_URI) ?: return Result.failure()
        val mediaUri = mediaString.toUri()
        return withContext(Dispatchers.IO) {
            try {
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
                        Log.d("HoangDH", resultData.toString())
                        cloudinaryUrl = resultData?.get(CLOUDINARY_IMAGE_URL).toString()
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        Log.d("HoangDH", error.toString())
                        cloudinaryUrl = null
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                        Log.d("HoangDH", error.toString())
                        cloudinaryUrl = null
                    }
                }).dispatch()
                if(cloudinaryUrl != null) {
                    return@withContext Result.success(workDataOf(CLOUDINARY_IMAGE_URL to cloudinaryUrl))
                } else {
                    return@withContext Result.failure()
                }
            } catch (e: Exception) {
                return@withContext Result.failure()
            }
        }

    }

    companion object {
        const val WORK_NAME = "UploadMediaWorker"
        const val MEDIA_URI = "media_uri"
        const val CLOUDINARY_IMAGE_URL = "secure_url"
    }
}