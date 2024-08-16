package com.example.doctorapp.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
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
}