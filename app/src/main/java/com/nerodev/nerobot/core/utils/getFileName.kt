package com.nerodev.nerobot.core.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun Uri.getFileName(context: Context): String? {
    val projection = arrayOf(OpenableColumns.DISPLAY_NAME)
    return context.contentResolver.query(this, projection, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        } else null
    }
}

fun Uri.getFileSize(context: Context): Long {
    return context.contentResolver.openAssetFileDescriptor(this, "r")?.use { descriptor ->
        descriptor.length
    } ?: 0L
}