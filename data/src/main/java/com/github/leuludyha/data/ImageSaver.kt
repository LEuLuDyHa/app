package com.github.leuludyha.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

/**
 * Provides utility methods to save, delete and load images using the internal storage.
 *
 * Taken from [Philipp Lackner – How to Use Internal Storage (Save, Load, Delete) - Android Studio Tutorial](https://www.youtube.com/watch?v=EeLz1DPMsW8)
 */
class ImageSaver {
    companion object {

        /**
         * Tries to load an image from the given [fileName] in the internal storage.
         *
         * @return the loaded image or null if the find is not found or usable
         */
        suspend fun loadImageToInternalStorage(context: Context, fileName: String): Bitmap? {
            return withContext(Dispatchers.IO) {
                val directory = context.filesDir
                val file = File(directory, fileName)

                if(!(file.canRead() && file.isFile && file.name.endsWith(".jpg"))) {
                    val bytes = file.readBytes()
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                }
                else null
            }
        }

        /**
         * Saves the given [Bitmap] image to the given [fileName] in the internal storage.
         *
         * @return true if the saving is successful, false otherwise
         */
        fun saveImageToInternalStorage(context: Context, fileName: String, bmp: Bitmap): Boolean {
            return try {
                context.openFileOutput("$fileName.jpg", MODE_PRIVATE).use {
                    if(!bmp.compress(Bitmap.CompressFormat.JPEG, 95, it))
                        throw IOException("Couldn't save bitmap.")
                }
                true
            } catch(e: IOException) {
                e.printStackTrace()
                false
            }
        }

        /**
         * Deletes the image at the given [fileName], if any is found.
         *
         * @return true if the deleting is successful, false otherwise
         */
        fun deleteImageFromInternalStorage(context: Context, fileName: String): Boolean {
            return try {
                context.deleteFile("$fileName.jpg")
            } catch(e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }
}