package com.nerodev.nerobot.core.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.InputStream

fun createTempFileFromStream(inputStream: InputStream, fileName: String?, context: Context): File {
    val tempFile = File(context.cacheDir, fileName ?: "temp_file")
    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return tempFile
}

fun Uri.getFileName(context: Context): String? {
    return context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        } else null
    }
}