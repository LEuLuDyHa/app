package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Cover
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RawDocumentTest: RequiringLibraryApiTest() {

    @Test
    fun `Fields are properly accessed`() {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected work`() { runTest {
        mockWebServer.enqueue(editionResponse)
        mockWebServer.enqueue(workResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val doc = RawDocument(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "/works/key",
            authorIds = listOf("/authors/authorId"),
            editionIds = listOf("key")
        )

        val res = doc.toModel(libraryApi)!!
        val ed = res.editions.first()[0]
        val edWorks = ed.works.first()
        val auth = res.authors.first()[0]

        assertThat(res.id).isEqualTo("key")
        assertThat(res.title).isEqualTo("Awesome book")
        assertThat(res.authors.first()).isEqualTo(listOf(mockAuthor))
        assertThat(res.covers.first()).isEqualTo(listOf(Cover(0)))
        assertThat(res.subjects.first()).isEmpty()
        assertThat(ed).isEqualTo(mockEdition)
        assertThat(edWorks).isEqualTo(listOf(mockWork))
        assertThat(auth).isEqualTo(mockAuthor)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on invalid key`() { runTest {
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val doc = RawDocument(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "/work/key",
            authorIds = listOf("/authors/authorId"),
            editionIds = listOf("key")
        )
        assertThat(doc.toModel(libraryApi)).isNull()
    }}
}