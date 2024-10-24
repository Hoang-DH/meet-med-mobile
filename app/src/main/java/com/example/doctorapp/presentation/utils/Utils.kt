package com.example.doctorapp.presentation.utils

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.doctorapp.R
import com.google.android.material.snackbar.Snackbar

object Utils {
    fun showSnackBar(text: String, view: View) {
        // Show snackbar
        Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

}