package com.github.leuludyha.data.repository.datasourceImpl

import android.graphics.Bitmap
import com.github.leuludyha.data.repository.datasource.BitmapProvider
import com.squareup.picasso.Picasso

class BitmapProviderImpl: BitmapProvider {
    override fun invoke(path: String): Bitmap? {
        return try {
            Picasso.get().load(path).get()
        } catch(e: Exception) {
            null
        }
    }
}