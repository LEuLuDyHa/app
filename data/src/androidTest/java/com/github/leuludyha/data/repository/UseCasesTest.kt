package com.github.leuludyha.data.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.data.FileReader
import com.github.leuludyha.data.RequiringLibraryRepositoryTest
import com.github.leuludyha.data.api.RawDocument
import com.github.leuludyha.domain.useCase.GetAuthorRemotelyUseCase
import com.github.leuludyha.domain.useCase.SearchRemotelyUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection

/*
 * I couldn't write those tests at the domain layer because I need to instantiate an API and
 * a database etc, which are not available at that layer.
 *
 * Moreover I didn't write any RepositoryTest because every method of the repository should be
 * covered by UseCases.
 */

class UseCasesTest: RequiringLibraryRepositoryTest() {

    @Test
    fun searchRemotelyUseCaseGivesExpectedResult() = runBlocking {
        val json = FileReader
            .readResourceFromFile(InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("search_2docs.json")
            )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expectedDoc0 = RawDocument(
            title = "work0",
            coverId = 0,
            authorNames = listOf("Author0", "Author1"),
            firstPublishYear = 2023,
            key = "key0",
            authorIds = listOf("author0", "author1"),
            editionIds = listOf("edition0", "edition1", "edition2")
        )

        val expectedDoc1 = RawDocument(
            title = "work1",
            coverId = 1,
            authorNames = listOf("Author2"),
            firstPublishYear = 2023,
            key = "key1",
            authorIds = listOf("author2"),
            editionIds = listOf("edition3", "edition4")
        )

        val actualResponse = SearchRemotelyUseCase(libraryRepository)("query").first()
        // TODO How to test for flows of paging data?
    }


    @Test
    fun getAuthorRemotelyGivesCorrectResult() = runBlocking {
        val json = FileReader
            .readResourceFromFile(InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("search_2docs.json")
            )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expectedDoc0 = RawDocument(
            title = "work0",
            coverId = 0,
            authorNames = listOf("Author0", "Author1"),
            firstPublishYear = 2023,
            key = "key0",
            authorIds = listOf("author0", "author1"),
            editionIds = listOf("edition0", "edition1", "edition2")
        )

        val expectedDoc1 = RawDocument(
            title = "work1",
            coverId = 1,
            authorNames = listOf("Author2"),
            firstPublishYear = 2023,
            key = "key1",
            authorIds = listOf("author2"),
            editionIds = listOf("edition3", "edition4")
        )

        val actualResponse = GetAuthorRemotelyUseCase(libraryRepository)("query").first()
        // TODO How to test for flows of paging data?

        /*assertThat(result).isEqualTo(expected)
        assertThat(result.name).isEqualTo(expected.name)
        //assertThat(result.bio).isEqualTo(expected.bio)
        assertThat(result.wikipedia).isEqualTo(expected.wikipedia)
        assertThat(result.entityType).isEqualTo(expected.entityType)
        assertThat(result.photos.first()).isEqualTo(expected.photos.first())*/
    }
}