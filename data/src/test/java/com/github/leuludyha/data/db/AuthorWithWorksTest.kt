package com.github.leuludyha.data.db

import com.google.common.truth.Truth
import org.junit.Test

class AuthorWithWorksTest {
    @Test
    fun `Fields are properly accessed`() {
        val author = AuthorEntity(
            authorId = "id",
            null,
            null,
            null,
            null,
        )
        val authorWithWorks = AuthorWithWorks(
            author = author,
            works = listOf()
        )

        Truth.assertThat(authorWithWorks.author).isEqualTo(author)
        Truth.assertThat(authorWithWorks.works).isEmpty()
    }
}