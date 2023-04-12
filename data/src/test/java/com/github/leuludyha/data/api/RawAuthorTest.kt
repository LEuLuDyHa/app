package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Cover
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection

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
        assertThat(res.covers.first()).isEqualTo(listOf(Cover(1)))
        assertThat(work).isEqualTo(mockWork)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on invalid key`() { runTest {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `covers empty on null photoIds`() { runTest {
        val author = RawAuthor(
            key = "/authors/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = null,
            error = null
        )

        val model = author.toModel(libraryApi)
        assertThat(model?.covers?.first()).isEmpty()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `covers empty on negative photoIds`() { runTest {
        val author = RawAuthor(
            key = "/authors/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(-1, -2),
            error = null
        )

        val model = author.toModel(libraryApi)
        assertThat(model?.covers?.first()).isEmpty()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `covers uniquely defined on duplicate photoIds`() { runTest {
        val author = RawAuthor(
            key = "/authors/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(2, 2),
            error = null
        )

        val model = author.toModel(libraryApi)
        assertThat(model?.covers?.first()).isEqualTo(listOf( Cover(2L)))
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `works empty on invalid work key`() { runTest {
        val wrongWorkJson =
            """
                { "key": "/work/OL45804W" }
            """.trimIndent()
        val wrongResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(wrongWorkJson)
        mockWebServer.enqueue(wrongResponse)

        val author = RawAuthor(
            key = "/authors/x",
            wikipedia = "x",
            name = "x",
            birthDate = "x",
            deathDate = "x",
            photoIds = listOf(2, 2),
            error = null
        )

        val model = author.toModel(libraryApi)
        assertThat(model?.works?.first()).isEmpty()
    }}
}