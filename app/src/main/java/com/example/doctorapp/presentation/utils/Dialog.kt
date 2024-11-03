package com.example.doctorapp.presentation.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.example.doctorapp.R

object Dialog {
    fun showCongratulationDialog(
        context: Context,
        message: String,
        isShowLoading: Boolean,
        onLoading: (() -> Unit) ? = null,
        onClickDone: (() -> Unit) ? = null,
        onClickEdit: (() -> Unit) ? = null
    ) {
        val builder = AlertDialog.Builder(context)
        val customView = LayoutInflater.from(context).inflate(
            R.layout.dialog_congratulation, null
        )
        builder.setView(customView)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setLayout(
                (context.resources.displayMetrics.widthPixels * 0.85).toInt(),
                (context.resources.displayMetrics.heightPixels * 0.5).toInt()
            )
            //dialog disappear after 3 seconds
            customView.postDelayed({
                dialog.dismiss()
//                onClickDone?.invoke()
            }, 3000)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showChooseImageDialog(
        context: Context,
        onClickCamera: () -> Unit,
        onClickGallery: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        val customView = LayoutInflater.from(context).inflate(
            R.layout.fragment_dialog_choose_image, null
        )
        builder.setView(customView)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        try {
            dialog.apply {
                show()
                setCancelable(true)
                setCanceledOnTouchOutside(true)
                window?.setLayout(
                    (context.resources.displayMetrics.widthPixels * 0.85).toInt(),
                    (context.resources.displayMetrics.heightPixels * 0.3).toInt()
                )
                window?.setGravity(Gravity.CENTER)
            }
            customView.findViewById<View>(R.id.btnCamera).setOnClickListener {
                onClickCamera.invoke()
                dialog.dismiss()
            }
            customView.findViewById<View>(R.id.btnGallery).setOnClickListener {
                onClickGallery.invoke()
                dialog.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        onClickOk: (() -> Unit) ? = null,
        onClickCancel: (() -> Unit) ? = null
    ) {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { _, _ ->
                onClickOk?.invoke()
            }
            setNegativeButton("Cancel") { dialog, _ ->
                onClickCancel?.invoke()
                dialog.dismiss()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


}