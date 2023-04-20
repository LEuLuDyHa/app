package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection

class RawAuthorTest: RequiringLibraryApiTest() {

    val testRawAuthor = RawAuthor(
        key = "/authors/id",
        wikipedia = "x",
        name = "x",
        birthDate = "x",
        deathDate = "x",
        photoIds = listOf(1),
        error = null
    )

    val testAuthor = Author(
        id = "id",
        "x",
        "x",
        "x",
        "x",
        flowOf(),
        flowOf()
    )

    @Test
    fun `Fields are properly accessed`() {
        assertThat(testRawAuthor.key).isEqualTo("/authors/id")
        assertThat(testRawAuthor.wikipedia).isEqualTo("x")
        assertThat(testRawAuthor.name).isEqualTo("x")
        assertThat(testRawAuthor.birthDate).isEqualTo("x")
        assertThat(testRawAuthor.deathDate).isEqualTo("x")
        assertThat(testRawAuthor.photoIds).isEqualTo(listOf(1L))
        assertThat(testRawAuthor.error).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected author`() { runTest {
        val res = testRawAuthor.toModel(libraryApi)!!
        assertThat(res).isEqualTo(testAuthor)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns null on invalid key`() { runTest {
        val rawAuthor = testRawAuthor.copy(key = "/invalid/x")
        assertThat(rawAuthor.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns null on error non null`() { runTest {
        val rawAuthor = testRawAuthor.copy(error = "error")
        assertThat(rawAuthor.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with expected covers`() { runTest {
        val model = testRawAuthor.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEqualTo(listOf(Cover(1))) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with empty covers on null photoIds`() { runTest {
        val author = testRawAuthor.copy(photoIds = null)
        val model = author.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with empty covers on only negative photoIds`() { runTest {
        val author = testRawAuthor.copy(photoIds = listOf(-1, -2))
        val model = author.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with uniquely defined covers on duplicate photoIds`() { runTest {
        val author = testRawAuthor.copy(photoIds = listOf(2, 2, 2))
        val model = author.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEqualTo(listOf(Cover(2))) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with correct works on error in response`() { runTest {
        val wrongWorkJson =
            """
               {
                  "error": "error"
                }
            """.trimIndent()
        val wrongResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(wrongWorkJson)
        mockWebServer.enqueue(wrongResponse)

        val model = testRawAuthor.toModel(libraryApi)!!
        model.works.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with expected works`() { runTest {
        mockWebServer.enqueue(authorWorksResponse)

        val res = testRawAuthor.toModel(libraryApi)!!
        res.works.collect { works -> assertThat(works).isEqualTo(listOf(mockWork)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns author with empty works on error in response`() { runTest {
        val wrongWorkJson =
            """
               {
                  "error": "error"
                }
            """.trimIndent()
        val wrongResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(wrongWorkJson)
        mockWebServer.enqueue(wrongResponse)

        val model = testRawAuthor.toModel(libraryApi)!!
        model.works.collect { assertThat(it).isEmpty() }
    }}
}