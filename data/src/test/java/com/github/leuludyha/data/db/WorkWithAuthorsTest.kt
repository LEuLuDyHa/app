package com.github.leuludyha.data.db

import com.google.common.truth.Truth
import org.junit.Test

class WorkWithAuthorsTest {
    @Test
    fun `Fields are properly accessed`() {
        val work = WorkEntity(
            workId = "id",
            null
        )

        val workWithAuthors = WorkWithAuthors(
            work = work,
            authors = listOf()
        )

        Truth.assertThat(workWithAuthors.work).isEqualTo(work)
        Truth.assertThat(workWithAuthors.authors).isEmpty()
    }
}