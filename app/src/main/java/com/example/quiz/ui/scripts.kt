package com.example.quiz.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

fun bitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun base64ToBitmap(base64String: String): Bitmap {
    val decodedBytes = Base64.decode(base64String, Base64.NO_WRAP)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}