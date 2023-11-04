package com.example.medicinalplant.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.medicinalplant.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileDescriptor
import java.io.IOException


fun predict(context: Context, uri: Uri): Int {
//     val model = model.newInstance(context)
//
//     val tfImage = TensorImage.fromBitmap(uriToBitmap(context, uri))
//    val outputs = model.process(tfImage.tensorBuffer)
//        .outputFeature0AsTensorBuffer

    val bitmap = uriToBitmap(context, uri)

    val imageProcessor = org.tensorflow.lite.support.image.ImageProcessor.Builder()
        .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
        .build()

    var tensorImage = TensorImage(DataType.FLOAT32)
    tensorImage.load(bitmap)

    tensorImage = imageProcessor.process(tensorImage)


    val model = Model.newInstance(context)

// Creates inputs for reference.
    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
    inputFeature0.loadBuffer(tensorImage.buffer)

// Runs model inference and gets result.
    val outputs = model.process(inputFeature0)
    val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

    var maxInd = 0

    outputFeature0.forEachIndexed {index, fl ->
        if (outputFeature0[maxInd] < fl) {
            maxInd = index
        }
    }

// Releases model resources if no longer used.
    model.close()
    return maxInd
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    try {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}




