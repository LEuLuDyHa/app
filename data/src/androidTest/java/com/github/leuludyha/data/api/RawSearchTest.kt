package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RawSearchTest: RequiringLibraryApiTest() {

    @Test
    fun fieldsAreProperlyGotten() {
        val doc = RawDocument(
            coverId = 0,
            title = "Awesome book",
            authorNames = listOf("Awesome guy"),
            firstPublishYear = 0,
            key = "/work/key",
            authorIds = listOf("/authors/authorId"),
            editionIds = listOf("key")
        )
        val search = RawSearch(
            documents = listOf(doc),
            error = "x"
        )

        assertThat(search.documents).isEqualTo(listOf(doc))
        assertThat(search.error).isEqualTo("x")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelReturnsExpectedWorks() { runBlocking {
        mockWebServer.enqueue(workResponse)

        val search = RawSearch(
            documents = listOf(mockRawDocument),
            error = null,
        )

        val works = search.toModel(libraryApi)

        assertThat(works).isEqualTo(listOf(mockWork))
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelFailsOnInvalidKey() { runBlocking {
        mockWebServer.enqueue(workResponse)

        val search = RawSearch(
            documents = listOf(mockRawDocument.copy(key = "/work/invalid")),
            error = null,
        )

        val works = search.toModel(libraryApi)

        assertThat(works).isEmpty()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelFailsOnError() { runBlocking {
        mockWebServer.enqueue(workResponse)

        val search = RawSearch(
            documents = listOf(mockRawDocument.copy(key = "/work/invalid")),
            error = "error",
        )

        val works = search.toModel(libraryApi)

        assertThat(works).isEmpty()
    }}
}