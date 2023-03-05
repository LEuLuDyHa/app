package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Document
import com.github.leuludyha.domain.util.FileReader
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchApiTest {
    private lateinit var api: SearchApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApi::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for multiple documents found, api must return all documents with http code 200`() = runTest {

        val json = FileReader.readResourceFromFile(this.javaClass.classLoader!!, "2docs_search.json")

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expectedDoc0 = Document(
            title = "TestBook0",
            coverId = 0,
            authorNames = listOf("TestAuthor0"),
            firstPublishYear = 2023,
            key = "TestKey0",
            authorKeys = listOf("TestAuthorKey0")
        )

        val expectedDoc1 = Document(
            title = "TestBook1",
            coverId = 1,
            authorNames = listOf("TestAuthor1"),
            firstPublishYear = 2023,
            key = "TestKey1",
            authorKeys = listOf("TestAuthorKey1")
        )

        val actualResponse = api.search("test").body()
        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse?.resultsCount).isEqualTo(2)
        assertThat(actualResponse?.documents).isEqualTo(listOf(expectedDoc0, expectedDoc1))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}