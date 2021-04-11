package com.halulkin.storm.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StyleRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showToast(data: Any, duration: Int = Toast.LENGTH_SHORT) {
    val msg = when (data) {
        is String -> data
        is Int -> getString(data)
        else -> ""
    }
    Toast.makeText(this, msg, duration).show()
}

