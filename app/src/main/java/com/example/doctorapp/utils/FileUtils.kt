package com.example.doctorapp.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

object FileUtils {

    fun getRealPathFromUri(uri: Uri?, context: Context): String? {
        // Get real path from uri
        uri ?: return null
        var path: String? = null
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }

}