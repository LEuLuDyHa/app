package com.github.leuludyha.data.db

import com.google.common.truth.Truth
import org.junit.Test

class EditionWithWorksTest {
    @Test
    fun `Fields are properly accessed`() {
        val edition = EditionEntity(
            editionId = "id",
            null,
            null,
            null,
        )
        val editionWithWorks = EditionWithWorks(
            edition = edition,
            works = listOf()
        )

        Truth.assertThat(editionWithWorks.edition).isEqualTo(edition)
        Truth.assertThat(editionWithWorks.works).isEmpty()
    }
}