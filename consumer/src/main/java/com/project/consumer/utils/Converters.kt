package com.project.consumer.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Converters {
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}