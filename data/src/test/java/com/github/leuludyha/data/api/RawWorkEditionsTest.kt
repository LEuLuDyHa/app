package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RawWorkEditionsTest: RequiringLibraryApiTest() {

    @Test
    fun `Fields are properly gotten`() {
        val workEditions = RawWorkEditions(
            links = RawWorkLinks("/works/OL45804W", "x", "x"),
            editionsCount = 1,
            editions = listOf(mockRawEdition),
            error = "x"
        )

        assertThat(workEditions.links).isEqualTo( RawWorkLinks("/works/OL45804W", "x", "x"))
        assertThat(workEditions.editionsCount).isEqualTo(1)
        assertThat(workEditions.editions).isEqualTo(listOf(mockRawEdition))
        assertThat(workEditions.error).isEqualTo("x")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel returns expected author`() { runTest {
        mockWebServer.enqueue(workResponse)

        val workEditions = RawWorkEditions(
            links = RawWorkLinks("/works/OL45804W", "x", "x"),
            editionsCount = 1,
            editions = listOf(mockRawEdition),
            error = null,
        )

        val res = workEditions.toModel(libraryApi)?.get(0)!!

        assertThat(res.works.first()).isEqualTo(listOf(mockWork))
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `toModel fails on error`() { runTest {
        mockWebServer.enqueue(workResponse)

        val workEditions = RawWorkEditions(
            links = RawWorkLinks("/works/OL45804W", "x", "x"),
            editionsCount = 1,
            editions = listOf(mockRawEdition),
            error = "x",
        )

        assertThat(workEditions.toModel(libraryApi)).isNull()
    }}
}