package com.github.leuludyha.domain.model.library

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CoverTest {
    @Test
    fun urlForSizeSmallIsCorrect() {
        val cover = Cover(1)
        assertThat(cover.urlForSize(CoverSize.Small)).isEqualTo("https://covers.openlibrary.org/b/id/1-S.jpg")
    }

    @Test
    fun equalsIsCorrect() {
        val cover = Cover(1)
        assertThat(cover).isEqualTo(cover)
        assertThat(cover).isNotEqualTo("test")
        assertThat(cover).isNotEqualTo(Cover(2))
    }
}