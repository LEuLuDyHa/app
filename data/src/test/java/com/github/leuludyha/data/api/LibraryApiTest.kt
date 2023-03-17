package com.github.leuludyha.data.api

import com.github.leuludyha.data.repository.LibraryRepositoryImpl
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryRemoteDataSourceImpl
import com.github.leuludyha.domain.repository.LibraryRepository
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryApiTest {
    private lateinit var api: LibraryApi
    private lateinit var remoteDataSource: LibraryRemoteDataSource
    private lateinit var repository: LibraryRepository

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryApi::class.java)
        remoteDataSource = LibraryRemoteDataSourceImpl(api)
        repository = LibraryRepositoryImpl(remoteDataSource)
    }

    /*@OptIn(ExperimentalCoroutinesApi::class)
    fun searchTwoDocsTest(dataProvider: suspend (String) -> RawSearch) = runTest {

        val json =
            FileReader.readResourceFromFile(this.javaClass.classLoader!!, "search_2docs.json")

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
            authorKeys = listOf("TestAuthorKey0"),
            editionKeys = null
        )

        val expectedDoc1 = Document(
            title = "TestBook1",
            coverId = 1,
            authorNames = listOf("TestAuthor1"),
            firstPublishYear = 2023,
            key = "TestKey1",
            authorKeys = listOf("TestAuthorKey1"),
            editionKeys = null
        )

        val actualResponse = dataProvider("test")
        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse.documents).isEqualTo(listOf(expectedDoc0, expectedDoc1))
    }*/

    /*@OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `On unsuccessful response, LibraryRemoteDataSource returns Result_Error`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_METHOD)
        mockWebServer.enqueue(mockResponse)

        val actualResponse = remoteDataSource.search("test")
        assertThat(actualResponse).isInstanceOf(Result.Error::class.java)
    }*/

    /*@OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `real test`() = runTest {
        val realApi = Retrofit.Builder()
            .baseUrl("https://openlibrary.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryApi::class.java)
        val realRemoteDataSource = LibraryRemoteDataSourceImpl(realApi)
        val realRepository = LibraryRepositoryImpl(realRemoteDataSource)

        val res = realRepository.editionById("OL7353617M")
        println("## ${res.data}")
    }*/

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}