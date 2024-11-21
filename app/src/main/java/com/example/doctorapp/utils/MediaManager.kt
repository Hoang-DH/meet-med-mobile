package com.example.doctorapp.utils

import android.content.Context
import com.cloudinary.android.MediaManager

object ApplicationMediaManager {
    private var mediaManager: Any? = null

    fun startMediaManager(context: Context) {
        if(mediaManager == null) {
            mediaManager = MediaManager.init(context)
        }
    }
}