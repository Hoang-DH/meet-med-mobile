package com.example.doctorapp.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import com.example.doctorapp.R
import com.example.doctorapp.databinding.DialogCongratulationBinding
import com.example.doctorapp.databinding.DialogErrorBinding
import com.example.doctorapp.databinding.DialogSortTypeBinding

object Dialog {
    fun showCongratulationDialog(
        context: Context,
        message: String,
        isShowLoading: Boolean,
        onClickDone: (() -> Unit)? = null,
        onClickEdit: (() -> Unit)? = null
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val binding = DataBindingUtil.inflate<DialogCongratulationBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_congratulation,
            null,
            false
        )

        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            llBtn.visibility = if (onClickEdit != null || onClickDone != null) View.VISIBLE else View.GONE
            btnDone.visibility = if (onClickDone != null) View.VISIBLE else View.GONE
            tvEditAppointment.visibility = if (onClickEdit != null) View.VISIBLE else View.GONE
            tvDescription.text = message
            progressBar.visibility = if (isShowLoading) View.VISIBLE else View.GONE
            btnDone.setOnClickListener {
                onClickDone?.invoke()
                dialog.dismiss()
            }
        }
        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setLayout(
                (context.resources.displayMetrics.widthPixels * 0.85).toInt(),
                (context.resources.displayMetrics.heightPixels * 0.5).toInt()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dialog
    }

    fun showDialogError(
        context: Context,
        message: String,
        onClickOk: (() -> Unit)? = null
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val binding = DataBindingUtil.inflate<DialogErrorBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_error,
            null,
            false
        )
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            tvDescription.text = message
            btnOk.setOnClickListener {
                onClickOk?.invoke()
                dialog.dismiss()
            }
        }
        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setLayout(
                (context.resources.displayMetrics.widthPixels * 0.85).toInt(),
                (context.resources.displayMetrics.heightPixels * 0.5).toInt()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dialog
    }


    fun dismissDialog(dialog: AlertDialog) {
        dialog.dismiss()
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
            dialog.show()
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(true)
            dialog.window?.apply {
                setLayout(
                    (context.resources.displayMetrics.widthPixels * 0.85).toInt(),
                    (context.resources.displayMetrics.heightPixels * 0.7).toInt()
                )
                setGravity(Gravity.CENTER_VERTICAL)
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
        onClickOk: (() -> Unit)? = null,
        onClickCancel: (() -> Unit)? = null
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


    fun showSortTypeDialog(
        onClickSortDefault: () -> Unit,
        onClickSortStarDes: () -> Unit,
        onClickSortStarAsc: () -> Unit,
        onClickSortReviewDes: () -> Unit,
        onClickSortReviewAsc: () -> Unit,
        view: View
    ) {
        var popupMenu: PopupWindow? = null
        fun dismissPopup() {
            if (popupMenu?.isShowing == true) {
                popupMenu?.dismiss()
            }
        }

        val binding = DataBindingUtil.inflate<DialogSortTypeBinding>(
            LayoutInflater.from(view.context),
            R.layout.dialog_sort_type,
            null,
            false
        )
        binding.apply {
            layoutDefault.setOnClickListener {
                onClickSortDefault.invoke()
                dismissPopup()
            }
            layoutStartDescending.setOnClickListener {
                onClickSortStarDes.invoke()
                dismissPopup()
            }
            layoutStarAscending.setOnClickListener {
                onClickSortStarAsc.invoke()
                dismissPopup()
            }
            layoutReviewDescending.setOnClickListener {
                onClickSortReviewDes.invoke()
                dismissPopup()
            }
            layoutReviewtAscending.setOnClickListener {
                onClickSortReviewAsc.invoke()
                dismissPopup()
            }
        }

        dismissPopup()
        popupMenu =
            PopupWindow(binding.root, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                isOutsideTouchable = true
                isFocusable = true
            }
        popupMenu.showAsDropDown(view)
    }


}