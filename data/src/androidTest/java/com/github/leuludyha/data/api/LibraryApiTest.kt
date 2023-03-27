package com.github.leuludyha.data.api

import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.data.FileReader
import com.github.leuludyha.data.RequiringLibraryApiTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection

class LibraryApiTest: RequiringLibraryApiTest() {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchGivesCorrectResultWithTwoDocuments() = runBlocking {

        val json = FileReader.readResourceFromFile(
            InstrumentationRegistry
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

        val actualResponse = libraryApi.search("test")
        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse.body()?.documents).isEqualTo(listOf(expectedDoc0, expectedDoc1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkGivesCorrectResult() = runBlocking {

        val json = FileReader.readResourceFromFile(
            InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("getWork.json")
        )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expected = RawWork(
            title = "Fantastic Mr Fox",
            key = "/works/OL45804W",
            rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/OL34184A"))),
            //description = "The main character of Fantastic Mr. Fox is an extremely clever anthropomorphized fox named Mr. Fox.",
            coverIds = listOf(6498519, 8904777, 108274, 233884, 1119236, -1, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458),
            subjects = listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction"),
            error = null
        )

        val result = libraryApi.getWork("OL45804W")
        assertThat(result).isNotNull()
        assertThat(result.body()).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionGivesCorrectResult() = runBlocking {

        val json = FileReader
            .readResourceFromFile(
                InstrumentationRegistry
                    .getInstrumentation()
                    .context
                    .assets
                    .open("getEdition.json")
            )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expected = RawEdition(
            title = "Fantastic Mr. Fox",
            key = "/books/OL44247403M",
            authorRawKeys = listOf(RawKey("/authors/OL34184A")),
            isbn10 = null,
            isbn13 = listOf("9780142418222"),
            coverIds = listOf(13269612),
            workRawKeys = listOf(RawKey("/works/OL45804W")),
            error = null
        )

        val result = libraryApi.getEdition("OL7353617M")
        assertThat(result).isNotNull()
        assertThat(result.body()).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorGivesCorrectResult() = runBlocking {

        val json = FileReader.readResourceFromFile(
            InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("getAuthor.json")
        )

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expected = RawAuthor(
            wikipedia = null,
            name = "Roald Dahl",
            birthDate = "13 September 1916",
            deathDate = "23 November 1990",
            //bio = "Roald Dahl was a British novelist, short story writer, and screenwriter.",
            key = "/authors/OL34184A",
            photoIds = listOf(9395323, 9395316, 9395314, 9395313, 6287214),
            error = null
        )

        val result = libraryApi.getAuthor("OL23919A")
        assertThat(result).isNotNull()
        assertThat(result.body()).isEqualTo(expected)
    }
}