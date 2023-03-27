package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.Cover
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RawWorkTest: RequiringLibraryApiTest() {

    @Test
    fun fieldsAreProperlyGotten() {
        val work = RawWork(
            key = "/works/x",
            title = "x",
            rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/x"))),
            coverIds = listOf(-1, 1, 2),
            subjects = listOf("x", "y"),
            error = "x",
        )

        assertThat(work.key).isEqualTo("/works/x")
        assertThat(work.title).isEqualTo("x")
        assertThat(work.rawAuthors).isEqualTo(listOf(RawWork.RawWorkAuthor(RawKey("/authors/x"))))
        assertThat(work.coverIds).isEqualTo(listOf(-1L, 1L, 2L))
        assertThat(work.subjects).isEqualTo(listOf("x", "y"))
        assertThat(work.error).isEqualTo("x")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelReturnsExpectedWork() { runBlocking {
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val work = RawWork(
            key = "/works/x",
            title = "x",
            rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/x"))),
            coverIds = listOf(-1, 1, 2),
            subjects = listOf("x", "y"),
            error = null,
        )

        val res = work.toModel(libraryApi)!!
        val ed = res.editions.first()[0]
        val auth = res.authors.first()[0]

        assertThat(res.id).isEqualTo("x")
        assertThat(res.title).isEqualTo("x")
        assertThat(res.authors.first()).isEqualTo(listOf(mockAuthor))
        assertThat(res.covers.first()).isEqualTo(listOf(Cover(1), Cover(2)))
        assertThat(res.subjects.first()).isEqualTo(listOf("x", "y"))
        assertThat(ed).isEqualTo(mockEdition)
        assertThat(auth).isEqualTo(mockAuthor)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelFailsOnInvalidKey() { runBlocking {
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val work = RawWork(
            key = "/work/x",
            title = "x",
            rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/x"))),
            coverIds = listOf(-1, 1, 2),
            subjects = listOf("x", "y"),
            error = "x",
        )
        assertThat(work.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelFailsOnError() { runBlocking {
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val work = RawWork(
            key = "/works/x",
            title = "x",
            rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/x"))),
            coverIds = listOf(-1, 1, 2),
            subjects = listOf("x", "y"),
            error = "x",
        )
        assertThat(work.toModel(libraryApi)).isNull()
    }}
}