package com.github.leuludyha.data.repository.datasource

import android.graphics.Bitmap

/**
 * Provides a bitmap given a path, if any.
 */
interface BitmapProvider {
    /**
     * Provides a [Bitmap] at the given [path], if any.
     */
    operator fun invoke(path: String): Bitmap?
}