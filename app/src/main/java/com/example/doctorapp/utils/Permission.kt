package com.example.doctorapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.fragment.app.FragmentActivity

object Permission {
//    fun checkPermissions(
//        context: Context?,
//        vararg permissions: String?
//    ): Boolean {
//        for (permission in permissions) {
//            if (ActivityCompat.checkSelfPermission(context!!, permission!!)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                return false
//            }
//        }
//        return true
//    }
//
//    fun requestPermissions(
//        activity: FragmentActivity?, requestCode: Int,
//        vararg permissions: String?,
//        name: String
//    ) {
//        when {
//            shouldShowRequestPermissionRationale(activity!!, permissions[0]!!) -> showDialog(
//                activity,
//                permissions[0]!!,
//                name,
//                requestCode
//            )
//
//            else -> ActivityCompat.requestPermissions(activity, permissions, requestCode)
//
//        }
//    }
//
//    private fun showDialog(activity: Activity, permission: String, name: String?, requestCode: Int) {
//        val builder = android.app.AlertDialog.Builder(activity)
//        builder.apply {
//            setMessage("Permission to access your $name is required to use this app")
//            setTitle("Permission required")
//            setPositiveButton("OK") { _, _ ->
//                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
//            }
//        }
//        val dialog = builder.create()
//        dialog.show()
//    }


}