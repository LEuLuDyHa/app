package com.github.leuludyha.data.datasource

import android.graphics.Bitmap
import com.github.leuludyha.data.repository.datasource.BitmapProvider
import com.github.leuludyha.domain.model.library.Mocks

/**
 * Mock implementation of a [BitmapProvider], always return the same [Bitmap]
 */
class MockBitmapProviderImpl: BitmapProvider {
    override fun invoke(path: String): Bitmap = Mocks.bitmap()
}