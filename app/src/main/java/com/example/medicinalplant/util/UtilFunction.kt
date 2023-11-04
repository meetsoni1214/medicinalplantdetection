package com.example.medicinalplant.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun changeLanguage(s: String) {
    // code to change the app language
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(s))
}

fun Context.createImageFile(): File {
    // create an image file nam
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
    return image
}