package com.github.leuludyha.domain.model.library

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CoverSizeTest {
    @Test
    fun smallToStringIsS() = assertThat(CoverSize.Small.toString()).isEqualTo("S")

    @Test
    fun mediumToStringIsM() = assertThat(CoverSize.Medium.toString()).isEqualTo("M")

    @Test
    fun largeToStringIsL() = assertThat(CoverSize.Large.toString()).isEqualTo("L")
}