package com.github.leuludyha.data.db

import com.google.common.truth.Truth
import org.junit.Test

class WorkWithEditionsTest {
    @Test
    fun `Fields are properly accessed`() {
        val work = WorkEntity(
            workId = "id",
            null
        )

        val workWithAuthors = WorkWithEditions(
            work = work,
            editions = listOf()
        )

        Truth.assertThat(workWithAuthors.work).isEqualTo(work)
        Truth.assertThat(workWithAuthors.editions).isEmpty()
    }
}