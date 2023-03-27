package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.Cover
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RawEditionTest: RequiringLibraryApiTest() {

    @Test
    fun `Fields are properly gotten`() {
        val edition = RawEdition(
            key = "/works/x",
            title = "x",
            isbn13 = listOf("x", "y"),
            isbn10 = listOf("x", "y"),
            authorRawKeys = listOf(RawKey("/authors/x")),
            workRawKeys = listOf(RawKey("/works/x")),
            coverIds = listOf(-1, 1, 2),
            error = "x",
        )

        assertThat(edition.key).isEqualTo("/works/x")
        assertThat(edition.title).isEqualTo("x")
        assertThat(edition.isbn10).isEqualTo(listOf("x", "y"))
        assertThat(edition.isbn10).isEqualTo(listOf("x", "y"))
        assertThat(edition.authorRawKeys).isEqualTo(listOf(RawKey("/authors/x")))
        assertThat(edition.coverIds).isEqualTo(listOf(-1L, 1L, 2L))
        assertThat(edition.workRawKeys).isEqualTo(listOf(RawKey("/works/x")))
        assertThat(edition.error).isEqualTo("x")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected edition`() { runTest {
        mockWebServer.enqueue(workResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val edition = RawEdition(
            key = "/books/x",
            title = "x",
            isbn13 = listOf("x", "y"),
            isbn10 = listOf("x", "y"),
            authorRawKeys = listOf(RawKey("/authors/x")),
            workRawKeys = listOf(RawKey("/works/x")),
            coverIds = listOf(-1, 1, 2),
            error = null,
        )

        val res = edition.toModel(libraryApi)!!
        val work = res.works.first()[0]
        val auth = res.authors.first()[0]

        assertThat(res.id).isEqualTo("x")
        assertThat(res.title).isEqualTo("x")
        assertThat(res.isbn10).isEqualTo("x")
        assertThat(res.isbn13).isEqualTo("x")
        assertThat(res.covers.first()).isEqualTo(listOf(Cover(1), Cover(2)))
        assertThat(work).isEqualTo(mockWork)
        assertThat(auth).isEqualTo(mockAuthor)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on invalid key`() { runTest {
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val edition = RawEdition(
            key = "/book/x",
            title = "x",
            isbn13 = listOf("x", "y"),
            isbn10 = listOf("x", "y"),
            authorRawKeys = listOf(RawKey("/authors/x")),
            workRawKeys = listOf(RawKey("/works/x")),
            coverIds = listOf(-1, 1, 2),
            error = null,
        )
        assertThat(edition.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on error`() { runTest {
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val edition = RawEdition(
            key = "/books/x",
            title = "x",
            isbn13 = listOf("x", "y"),
            isbn10 = listOf("x", "y"),
            authorRawKeys = listOf(RawKey("/authors/x")),
            workRawKeys = listOf(RawKey("/works/x")),
            coverIds = listOf(-1, 1, 2),
            error = "x",
        )
        assertThat(edition.toModel(libraryApi)).isNull()
    }}
}