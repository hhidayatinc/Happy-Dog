package com.example.happyvet.utils

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.happyvet.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILENAME_FORMAT = "dd-MM-yyyy"
private const val MAX_SIZE = 1000000
val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT, Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(ctx: Context): File{
    val storageDir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(app: Application): File{
    val mediaDir = app.externalMediaDirs.firstOrNull()?.let{
        File(it, app.resources.getString(R.string.app_name)).apply{ mkdirs() }
    }
    val outputDir = if(mediaDir != null && mediaDir.exists()) mediaDir else app.filesDir
    return File(outputDir, "$timeStamp.jpg")
}

fun reduceFileImage(file: File): File{
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do{
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    }while(streamLength> MAX_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun rotateImage(file: File, isBackCamera: Boolean = false){
    val matrix = Matrix()
    val bitmap = BitmapFactory.decodeFile(file.path)
    val rotation = if(isBackCamera) 90f else -90f
    matrix.postRotate(rotation)
    if(!isBackCamera){
        matrix.postScale(-1f, 1f, bitmap.width/2f, bitmap.height/2f)
    }
    val res = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    res.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
}

fun uriToFile(selectedImg: Uri, ctx: Context): File{
    val cr: ContentResolver = ctx.contentResolver
    val myFile = createCustomTempFile(ctx)
    val inputStream = cr.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also{len = it} > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()
    return myFile
}

fun urlToBitmap(src: String): Bitmap?{
    return try{
        val url = URL(src)
        val con = url.openConnection()
        con.doInput = true
        con.connect()
        val input = con.getInputStream()
        BitmapFactory.decodeStream(input)
    }catch(e: Exception){
        Log.d("error", e.message.toString())
        null
    }
}