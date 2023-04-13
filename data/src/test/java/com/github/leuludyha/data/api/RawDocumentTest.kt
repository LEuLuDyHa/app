package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Work
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RawDocumentTest: RequiringLibraryApiTest() {

    val testRawDoc = RawDocument(
        key = "/works/id",
        coverId = 1,
        title = "x",
        authorNames = listOf("x"),
        firstPublishYear = 0,
        authorIds = listOf("x"),
        editionIds = listOf("x")
    )

    val testWork = Work(
        id = "id",
        title = "x",
        editions = flowOf(),
        authors = flowOf(),
        covers = flowOf(),
        subjects = flowOf()
    )

    @Test
    fun `Fields are properly accessed`() {
        assertThat(testRawDoc.coverId).isEqualTo(1)
        assertThat(testRawDoc.title).isEqualTo("x")
        assertThat(testRawDoc.authorNames).isEqualTo(listOf("x"))
        assertThat(testRawDoc.firstPublishYear).isEqualTo(0)
        assertThat(testRawDoc.key).isEqualTo("/works/id")
        assertThat(testRawDoc.authorIds).isEqualTo(listOf("x"))
        assertThat(testRawDoc.editionIds).isEqualTo(listOf("x"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected work`() { runTest {
        val res = testRawDoc.toModel(libraryApi)!!
        assertThat(res).isEqualTo(testWork)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns null on invalid key`() { runTest {
        val rawDoc = testRawDoc.copy(key = "/invalid/x")
        assertThat(rawDoc.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with expected editions`() { runTest {
        mockWebServer.enqueue(editionResponse)

        val model = testRawDoc.toModel(libraryApi)!!
        model.editions.collect { assertThat(it).isEqualTo(listOf(mockEdition)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty editions on null editionIds`() { runTest {
        mockWebServer.enqueue(editionResponse)

        val author = testRawDoc.copy(editionIds = null)
        val model = author.toModel(libraryApi)!!

        model.editions.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with unique editions on duplicated editionIds`() { runTest {
        mockWebServer.enqueue(editionResponse)
        mockWebServer.enqueue(editionResponse)
        mockWebServer.enqueue(editionResponse)

        val author = testRawDoc.copy(editionIds = listOf("x", "x", "x"))
        val model = author.toModel(libraryApi)!!

        model.editions.collect { assertThat(it).isEqualTo(listOf(mockEdition)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with expected authors`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val model = testRawDoc.toModel(libraryApi)!!
        model.authors.collect { assertThat(it).isEqualTo(listOf(mockAuthor)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty authors on null authorIds`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val rawDoc = testRawDoc.copy(authorIds = null)
        val model = rawDoc.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with unique authors on duplicated authorIds`() { runTest {
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val rawDoc = testRawDoc.copy(authorIds = listOf("x", "x", "x"))
        val model = rawDoc.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEqualTo(listOf(mockAuthor)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with expected cover`() { runTest {
        val model = testRawDoc.toModel(libraryApi)!!
        model.covers.collect { assertThat(it).isEqualTo(listOf(Cover(1))) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty covers on null coverId`() { runTest { mockWebServer.enqueue(authorResponse)
        val rawDoc = testRawDoc.copy(coverId = null)
        val model = rawDoc.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty covers on negative coverId`() { runTest { mockWebServer.enqueue(authorResponse)
        val rawDoc = testRawDoc.copy(coverId = -1)
        val model = rawDoc.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty subjects`() { runTest { mockWebServer.enqueue(authorResponse)
        val model = testRawDoc.toModel(libraryApi)!!

        model.subjects.collect { assertThat(it).isEmpty() }
    }}
}