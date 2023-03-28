package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.Cover
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RawAuthorTest: RequiringLibraryApiTest() {

    @Test
    fun `Fields are properly accessed`() {
        val author = RawAuthor(
            key = "x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(0, 1),
            error = "x"
        )

        assertThat(author.key).isEqualTo("x")
        assertThat(author.wikipedia).isEqualTo("x")
        assertThat(author.name).isEqualTo("x")
        assertThat(author.birthDate).isEqualTo("x")
        assertThat(author.deathDate).isEqualTo("x")
        assertThat(author.photoIds).isEqualTo(listOf(0L, 1L))
        assertThat(author.error).isEqualTo("x")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected author`() { runTest {
        mockWebServer.enqueue(authorWorksResponse)

        val author = RawAuthor(
            key = "/authors/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(-1, 1),
            error = null
        )

        val res = author.toModel(libraryApi)!!
        val work = res.works.first()[0]

        assertThat(res.id).isEqualTo("x")
        assertThat(res.name).isEqualTo("x")
        assertThat(res.wikipedia).isEqualTo("x")
        assertThat(res.birthDate).isEqualTo("x")
        assertThat(res.deathDate).isEqualTo("x")
        assertThat(res.photos.first()).isEqualTo(listOf(Cover(1)))
        assertThat(work).isEqualTo(mockWork)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on invalid key`() { runTest {
        mockWebServer.enqueue(authorWorksResponse)

        val author = RawAuthor(
            key = "/author/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(-1, 1),
            error = null
        )

        assertThat(author.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on error`() { runTest {
        mockWebServer.enqueue(authorWorksResponse)

        val author = RawAuthor(
            key = "/authors/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(-1, 1),
            error = "x"
        )

        assertThat(author.toModel(libraryApi)).isNull()
    }}
}