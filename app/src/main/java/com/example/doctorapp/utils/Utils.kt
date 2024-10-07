package com.example.doctorapp.utils

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
    object Spanner {
        fun spanString(
            spanTv: TextView,
            textView: String,
            resources: Resources,
            navCallBack: () -> Unit
        ) {
            var isClicked = false
            val spanTvString = spanTv.text.toString()
            val spannableString = SpannableString(spanTvString)
            spannableString.apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            spanTv.context,
                            R.color.color_1c64f2
                        )
                    ),
                    spanTvString.indexOf(textView),
                    spanTvString.indexOf(textView) + textView.length,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )

                setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            isClicked = true
                            navCallBack.invoke()
                            spanTv.text = spannableString
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = isClicked
                            ds.bgColor = ContextCompat.getColor(
                                spanTv.context,
                                android.R.color.transparent
                            )
                        }
                    }, spanTvString.indexOf(textView),
                    spanTvString.indexOf(textView) + textView.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            spanTv.text = spannableString
            spanTv.movementMethod = LinkMovementMethod.getInstance()
        }

    }

    fun showSnackBar(text: String, view: View) {
        // Show snackbar
        Snackbar.make(
            view,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

}