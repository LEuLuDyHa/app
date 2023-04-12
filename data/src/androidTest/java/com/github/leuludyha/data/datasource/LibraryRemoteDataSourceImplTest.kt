package com.github.leuludyha.data.datasource

import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.io.FileReader
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryRemoteDataSourceImpl
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection

class LibraryRemoteDataSourceImplTest {

    private lateinit var remoteDataSource: LibraryRemoteDataSource
    private lateinit var mockWebServer: MockWebServer

    private lateinit var searchResponse: MockResponse
    private lateinit var workResponse: MockResponse
    private lateinit var authorResponse: MockResponse
    private lateinit var editionResponse: MockResponse
    private lateinit var errorResponse: MockResponse

    @Before
    fun setup() {
        fun readFromJsonFile(fileName: String) = FileReader
            .readResourceFromFile(
                InstrumentationRegistry
                    .getInstrumentation()
                    .context
                    .assets
                    .open(fileName)
            )

        mockWebServer = MockWebServer()
        mockWebServer.start()
        val libraryApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryApi::class.java)

        val editionJson = readFromJsonFile("getEdition.json")
        val authorJson = readFromJsonFile("getAuthor.json")
        val workJson = readFromJsonFile("getWork.json")
        val searchJson = readFromJsonFile("search_2docs.json")

        editionResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(editionJson)
        authorResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(authorJson)
        workResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(workJson)
        searchResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(searchJson)
        errorResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            .setBody("error")

        remoteDataSource = LibraryRemoteDataSourceImpl(libraryApi)
    }

    @Test
    fun searchGivesCorrectResult() = runBlocking {
        mockWebServer.enqueue(searchResponse)

        val result = remoteDataSource.search("query").first()
        assertThat(result).isNotNull()

        // TODO how to test PagingData content ?
    }

    @Test
    fun getWorkGivesCorrectResultOnSuccess() = runBlocking {
        mockWebServer.enqueue(workResponse)

        val result = remoteDataSource.getWork(workMrFox.id).first().data
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(workMrFox)
    }

    @Test
    fun getWorkGivesErrorOnError() = runBlocking {
        mockWebServer.enqueue(errorResponse)

        val result = remoteDataSource.getWork("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getAuthorGivesCorrectResultOnSuccess() = runBlocking {
        mockWebServer.enqueue(authorResponse)

        val result = remoteDataSource.getAuthor(authorRoaldDahl.id).first().data
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(authorRoaldDahl)
    }

    @Test
    fun getAuthorGivesErrorOnError() = runBlocking {
        mockWebServer.enqueue(errorResponse)

        val result = remoteDataSource.getAuthor("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionGivesCorrectResultOnSuccess() = runBlocking {
        mockWebServer.enqueue(editionResponse)

        val result = remoteDataSource.getEdition(editionMrFox.id).first().data
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionGivesErrorOnError() = runBlocking {
        mockWebServer.enqueue(errorResponse)

        val result = remoteDataSource.getEdition("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionByISBNGivesCorrectResultOnSuccess() = runBlocking {
        mockWebServer.enqueue(editionResponse)

        val result = remoteDataSource.getEditionByISBN(editionMrFox.isbn13!!).first().data
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionByISBNGivesErrorOnError() = runBlocking {
        mockWebServer.enqueue(errorResponse)

        val result = remoteDataSource.getEditionByISBN("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @After
    @Throws(IOException::class)
    fun shutdown() {
        mockWebServer.shutdown()
    }
}