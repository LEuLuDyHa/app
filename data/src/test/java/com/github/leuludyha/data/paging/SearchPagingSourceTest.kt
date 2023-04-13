package com.github.leuludyha.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.leuludyha.data.RequiringLibraryApiTest
import com.github.leuludyha.domain.model.library.Work
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class SearchPagingSourceTest: RequiringLibraryApiTest() {
    lateinit var searchPagingSource: SearchPagingSource

    @Before
    fun init() {
        searchPagingSource = SearchPagingSource(libraryApi, "query")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns an error on http error`() = runTest {
        val errorResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(errorResponse)

        assertThat(searchPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns an error on json error`() = runTest {
        val errorResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(errorResponse)

        assertThat(searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        ).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns correct Page on multiple docs, page 2`() = runTest {
        val work0 = Work(
            id = "id0", null, flowOf(), flowOf(), flowOf(), flowOf()
        )
        val work1 = Work(
            id = "id1", null, flowOf(), flowOf(), flowOf(), flowOf()
        )
        val expected = listOf(work0, work1)

        val expectedResult = PagingSource.LoadResult.Page(
            data = expected,
            prevKey = 1,
            nextKey = 3
        )

        mockWebServer.enqueue(searchResponse)
        assertThat(
            searchPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 2,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(expectedResult)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns correct Page on multiple docs, page 1`() = runTest {
        val work0 = Work(
            id = "id0", null, flowOf(), flowOf(), flowOf(), flowOf()
        )
        val work1 = Work(
            id = "id1", null, flowOf(), flowOf(), flowOf(), flowOf()
        )
        val expected = listOf(work0, work1)

        val expectedResult = PagingSource.LoadResult.Page(
            data = expected,
            prevKey = null,
            nextKey = 2
        )

        mockWebServer.enqueue(searchResponse)
        assertThat(
            searchPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(expectedResult)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load returns correct Page on empty docs`() = runTest {
        val emptySearchJson =
            """
                {
                  "numFound": 0,
                  "start": 0,
                  "numFoundExact": true,
                  "docs": [ ]
                }
            """.trimIndent()
        val emptySearchResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(emptySearchJson)

        mockWebServer.enqueue(emptySearchResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )

        assertThat(
            searchPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(expectedResult)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getRefreshKey returns anchorPosition`() = runTest {
        val pages = listOf<PagingSource.LoadResult.Page<Int, Work>>()
        val config = PagingConfig(
            20,
            10,
            false,
            30,
            100,
            5
        )
        assertThat(searchPagingSource.getRefreshKey(
            PagingState(
                pages,
                1,
                config,
                0
            )
        )).isEqualTo(1)
    }
}