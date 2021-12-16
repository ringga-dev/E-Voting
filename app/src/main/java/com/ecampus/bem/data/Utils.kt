package com.ecampus.bem.data


import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_SHORT
    ).also { snackbar ->
        snackbar.setAction("OK") {
            snackbar.dismiss()
        }
    }.show()
}

