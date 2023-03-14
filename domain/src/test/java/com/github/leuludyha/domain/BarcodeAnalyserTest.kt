package com.github.leuludyha.domain

import com.github.leuludyha.domain.model.BarcodeAnalyser
import junit.framework.TestCase.assertEquals
import org.junit.Test

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
}
