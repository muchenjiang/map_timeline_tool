package com.lavacrafter.maptimelinetool

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun createPointPhotoFile(context: Context): File {
    val photoDir = File(context.filesDir, "point_photos").apply { mkdirs() }
    return File.createTempFile("point_photo_", ".jpg", photoDir)
}

fun getPointPhotoUri(context: Context, file: File): Uri =
    FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

fun deletePointPhoto(path: String?) {
    if (path.isNullOrBlank()) return
    runCatching { File(path).takeIf { it.exists() }?.delete() }
}

fun decodePointPhoto(path: String, maxDimension: Int = 1200): Bitmap? {
    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeFile(path, bounds)
    if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return null

    var sampleSize = 1
    while (bounds.outWidth / sampleSize > maxDimension || bounds.outHeight / sampleSize > maxDimension) {
        sampleSize *= 2
    }

    return BitmapFactory.decodeFile(
        path,
        BitmapFactory.Options().apply { inSampleSize = sampleSize }
    )
}
