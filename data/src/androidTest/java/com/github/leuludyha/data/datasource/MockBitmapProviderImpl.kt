package com.github.leuludyha.data.datasource

import android.content.Context
import android.graphics.Bitmap
import androidx.core.R
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.github.leuludyha.data.repository.datasource.BitmapProvider

class MockBitmapProviderImpl(private val context: Context): BitmapProvider {
    override fun invoke(path: String): Bitmap? =
        ContextCompat.getDrawable(context, R.drawable.ic_call_answer)?.toBitmap()
}