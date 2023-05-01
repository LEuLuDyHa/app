package com.github.leuludyha.data.repository.datasource

import android.graphics.Bitmap

interface BitmapProvider {
    operator fun invoke(path: String): Bitmap?
}