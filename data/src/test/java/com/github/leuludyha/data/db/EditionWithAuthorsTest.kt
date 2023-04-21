package com.github.leuludyha.data.db

import com.google.common.truth.Truth
import org.junit.Test

class EditionWithAuthorsTest {
    @Test
    fun `Fields are properly accessed`() {
        val edition = EditionEntity(
            editionId = "id",
            null,
            null,
            null,
        )
        val editionWithWorks = EditionWithAuthors(
            edition = edition,
            authors = listOf()
        )

        Truth.assertThat(editionWithWorks.edition).isEqualTo(edition)
        Truth.assertThat(editionWithWorks.authors).isEmpty()
    }
}