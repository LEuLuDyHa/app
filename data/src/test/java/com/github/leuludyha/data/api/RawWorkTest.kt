package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Work
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection

class RawWorkTest: RequiringLibraryApiTest() {

    val testRawWork = RawWork(
        key = "/works/id",
        title = "x",
        rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/id"))),
        coverIds = listOf(1),
        subjects = listOf("x"),
        error = null
    )

    val testWork = Work(
        id = "id",
        title = "x",
        editions = flowOf(),
        authors = flowOf(),
        covers = flowOf(),
        subjects = flowOf(),
    )

    @Test
    fun `Fields are properly accessed`() {
        assertThat(testRawWork.key).isEqualTo("/works/id")
        assertThat(testRawWork.title).isEqualTo("x")
        assertThat(testRawWork.rawAuthors).isEqualTo(listOf(RawWork.RawWorkAuthor(RawKey("/authors/id"))))
        assertThat(testRawWork.coverIds).isEqualTo(listOf(1L))
        assertThat(testRawWork.subjects).isEqualTo(listOf("x"))
        assertThat(testRawWork.error).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected work`() { runTest {
        val res = testRawWork.toModel(libraryApi)!!
        assertThat(res).isEqualTo(testWork)
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns null on invalid key`() { runTest {
        val raw = testRawWork.copy(key = "/invalid/x")
        assertThat(raw.toModel(libraryApi)).isNull()
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with expected editions`() { runTest {
        mockWebServer.enqueue(workEditionsResponse)

        val model = testRawWork.toModel(libraryApi)!!
        model.editions.collect { assertThat(it).isEqualTo(mockWorkEditions) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty editions on invalid response`() { runTest {
        val invalidWorksJson =
            """
                {
                  "error": "error"
                }
            """.trimIndent()
        val invalidResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(invalidWorksJson)
        mockWebServer.enqueue(invalidResponse)

        val model = testRawWork.toModel(libraryApi)!!

        model.editions.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with unique editions on duplicated edition keys`() { runTest {
        val invalidWorksJson =
            """
                {
                  "links": {
                    "self": "/works/OL45804W/editions.json",
                    "work": "/works/OL45804W",
                    "next": "/works/OL45804W/editions.json?offset=50"
                  },
                  "size": 1,
                  "entries": [
                    {
                      "key": "/books/OL44247403M"
                    },
                    {
                      "key": "/books/OL44247403M"
                    },
                    {
                      "key": "/books/OL44247403M"
                    }
                  ]
                }
            """.trimIndent()
        val invalidResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(invalidWorksJson)
        mockWebServer.enqueue(invalidResponse)

        val model = testRawWork.toModel(libraryApi)!!

        model.editions.collect { assertThat(it).isEqualTo(listOf(mockEdition)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with expected authors`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val model = testRawWork.toModel(libraryApi)!!
        model.authors.collect { assertThat(it).isEqualTo(listOf(mockAuthor)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty authors on null rawAuthors`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val raw = testRawWork.copy(rawAuthors = null)
        val model = raw.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty authors on invalid authors key`() { runTest {
        mockWebServer.enqueue(authorResponse)

        val raw = testRawWork.copy(rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/invalid/id"))))
        val model = raw.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with unique authors on duplicated rawAuthors`() { runTest {
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(authorResponse)

        val rawAuthor = RawWork.RawWorkAuthor(RawKey("/authors/id"))
        val raw = testRawWork.copy(rawAuthors = listOf(rawAuthor, rawAuthor, rawAuthor))
        val model = raw.toModel(libraryApi)!!

        model.authors.collect { assertThat(it).isEqualTo(listOf(mockAuthor)) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with expected cover`() { runTest {
        val model = testRawWork.toModel(libraryApi)!!
        model.covers.collect { assertThat(it).isEqualTo(listOf(Cover(1))) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty covers on null coverIds`() { runTest { mockWebServer.enqueue(authorResponse)
        val raw = testRawWork.copy(coverIds = null)
        val model = raw.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty covers on only negative coverIds`() { runTest { mockWebServer.enqueue(authorResponse)
        val raw = testRawWork.copy(coverIds = listOf(-1, -2, -3))
        val model = raw.toModel(libraryApi)!!

        model.covers.collect { assertThat(it).isEmpty() }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with correct subjects`() { runTest {
        val model = testRawWork.toModel(libraryApi)!!
        model.subjects.collect { assertThat(it).isEqualTo(listOf("x")) }
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns work with empty subjects on null raw subjects`() { runTest {
        val raw = testRawWork.copy(subjects = null)
        val model = raw.toModel(libraryApi)!!
        model.subjects.collect { assertThat(it).isEmpty() }
    }}
}