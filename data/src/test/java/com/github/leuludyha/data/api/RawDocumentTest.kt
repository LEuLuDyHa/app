package com.github.leuludyha.data.api

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RawDocumentTest {

    @Test
    fun `Properties of a document are properly gotten`() {
        val doc = RawDocument(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "Awesome key",
            authorIds = listOf("Weird key"),
            editionIds = listOf("key")
        )

        assertThat(doc.coverId).isEqualTo(0)
        assertThat(doc.title).isEqualTo("Awesome book")
        assertThat(doc.authorNames).isEqualTo(listOf("Awesome guy"))
        assertThat(doc.firstPublishYear).isEqualTo(0)
        assertThat(doc.key).isEqualTo("Awesome key")
        assertThat(doc.authorIds).isEqualTo(listOf("Weird key"))
        assertThat(doc.editionIds).isEqualTo(listOf("key"))
    }
}