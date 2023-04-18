package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RawEditionTest: RequiringLibraryApiTest() {

    val testRawEdition = RawEdition(
        key = "/books/id",
        title = "x",
        isbn10 = listOf("x"),
        isbn13 = listOf("x"),
        authorRawKeys = listOf(RawKey("/authors/x")),
        workRawKeys = listOf(RawKey("/works/x")),
        coverIds = listOf(1),
        error = null
    )

    val testEdition = Edition(
        id = "id",
        title = "x",
        isbn10 = "x",
        isbn13 = "x",
        authors = flowOf(),
        works = flowOf(),
        covers = flowOf()
    )

    @Test
    fun `Fields are properly accessed`() {
        assertThat(testRawEdition.key).isEqualTo("/books/id")
        assertThat(testRawEdition.title).isEqualTo("x")
        assertThat(testRawEdition.isbn10).isEqualTo(listOf("x"))
        assertThat(testRawEdition.isbn13).isEqualTo(listOf("x"))
        assertThat(testRawEdition.authorRawKeys).isEqualTo(listOf(RawKey("/authors/x")))
        assertThat(testRawEdition.workRawKeys).isEqualTo(listOf(RawKey("/works/x")))
        assertThat(testRawEdition.coverIds).isEqualTo(listOf(1L))
        assertThat(testRawEdition.error).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected edition`() { runTest {
        val res = testRawEdition.toModel(libraryApi)!!
        assertThat(res).isEqualTo(testEdition)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns null on invalid key`() { runTest {
        val raw = testRawEdition.copy(key = "/invalid/x")
        assertThat(raw.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with expected works`() { runTest {
        mockWebServer.enqueue(workResponse)

        val model = testRawEdition.toModel(libraryApi)!!
        model.works.collect { assertThat(it).isEqualTo(listOf(mockWork)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with empty works on null workRawKeys`() { runTest {
        mockWebServer.enqueue(workResponse)

        val author = testRawEdition.copy(workRawKeys = null)
        val model = author.toModel(libraryApi)!!

        model.works.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with unique works on duplicated workRawKeys`() { runTest {
        mockWebServer.enqueue(workResponse)
        mockWebServer.enqueue(workResponse)
        mockWebServer.enqueue(workResponse)

        val author = testRawEdition.copy(workRawKeys = listOf("/works/x", "/works/x", "/works/x").map { RawKey(it) })
        val model = author.toModel(libraryApi)!!

        model.works.collect { assertThat(it).isEqualTo(listOf(mockWork)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with expected authors`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val model = testRawEdition.toModel(libraryApi)!!
        model.authors.collect { assertThat(it).isEqualTo(listOf(mockAuthor)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with empty authors on null authorRawKeys`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val raw = testRawEdition.copy(authorRawKeys = null)
        val model = raw.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with unique authors on duplicated authorRawKeys`() { runTest {
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val raw = testRawEdition.copy(authorRawKeys = listOf("/authors/x", "/authors/x", "/authors/x").map { RawKey(it) })
        val model = raw.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEqualTo(listOf(mockAuthor)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with expected cover`() { runTest {
        val model = testRawEdition.toModel(libraryApi)!!
        model.covers.collect { assertThat(it).isEqualTo(listOf(Cover(1))) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with empty covers on null coverIds`() { runTest { mockWebServer.enqueue(authorResponse)
        val raw = testRawEdition.copy(coverIds = null)
        val model = raw.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns edition with empty covers on only negative coverIds`() { runTest { mockWebServer.enqueue(authorResponse)
        val raw = testRawEdition.copy(coverIds = listOf(-1, -2, -3))
        val model = raw.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}
}