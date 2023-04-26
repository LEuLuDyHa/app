package com.github.leuludyha.domain

import android.graphics.Rect
import android.media.Image
import androidx.camera.core.ImageInfo
import androidx.camera.core.ImageProxy
import com.github.leuludyha.domain.model.library.BarcodeAnalyser
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BarcodeAnalyserTest {

    @Test
    fun checkISBNCode_fails_on_format_different_than_13() {
        assertEquals(false, BarcodeAnalyser.checkISBNCode(""))
        assertEquals(false, BarcodeAnalyser.checkISBNCode("12345678901234"))
    }

    @Test
    fun checkISBNCode_accepts_correct_codes() {
        assertEquals(true, BarcodeAnalyser.checkISBNCode("9788420649986"))
        assertEquals(true, BarcodeAnalyser.checkISBNCode("9780306406157"))
    }

    @Test
    fun checkISBNCode_rejects_wrong_codes() {
        assertEquals(false, BarcodeAnalyser.checkISBNCode("97884206499A6"))
        assertEquals(false, BarcodeAnalyser.checkISBNCode("9788420649976"))
    }

    @Test
    fun analyseDoesNotFailOnNullImage() {
        val analyser = BarcodeAnalyser {}
        assertDoesNotThrow { analyser.analyze(MockImageProxy(null)) }
    }

    class MockImageProxy(
        private val image: Image? = null
    ): ImageProxy{
        override fun close() {

        }

        override fun getCropRect(): Rect {
            throw NotImplementedError()
        }

        override fun setCropRect(rect: Rect?) {
            throw NotImplementedError()
        }

        override fun getFormat(): Int {
            throw NotImplementedError()
        }

        override fun getHeight(): Int {
            throw NotImplementedError()
        }

        override fun getWidth(): Int {
            throw NotImplementedError()
        }

        override fun getPlanes(): Array<ImageProxy.PlaneProxy> {
            throw NotImplementedError()
        }

        override fun getImageInfo(): ImageInfo {
            throw NotImplementedError()
        }

        override fun getImage(): Image? = image
    }
}
