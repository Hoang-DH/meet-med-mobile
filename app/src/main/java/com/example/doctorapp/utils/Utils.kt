package com.example.doctorapp.utils

import android.view.View
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