package com.github.leuludyha.data.api

import com.github.leuludyha.data.FileReader
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

class LibraryApiTest {
    private lateinit var api: LibraryApi
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
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchTwoDocsTest() = runTest {

        val json = FileReader.readResourceFromFile(this.javaClass.classLoader!!, "search_2docs.json")

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expectedDoc0 = Document(
            title = "work0",
            coverId = 0,
            authorNames = listOf("Author0", "Author1"),
            firstPublishYear = 2023,
            key = "key0",
            authorIds = listOf("author0", "author1"),
            editionIds = listOf("edition0", "edition1", "edition2")
        )

        val expectedDoc1 = Document(
            title = "work1",
            coverId = 1,
            authorNames = listOf("Author2"),
            firstPublishYear = 2023,
            key = "key1",
            authorIds = listOf("author2"),
            editionIds = listOf("edition3", "edition4")
        )

        val actualResponse = api.search("test")
        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse.body()?.documents).isEqualTo(listOf(expectedDoc0, expectedDoc1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkGivesCorrectResult() = runTest {

        val json = FileReader.readResourceFromFile(this.javaClass.classLoader!!, "getWork.json")

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expected = RawWork(
            title = "Fantastic Mr Fox",
            key = "/works/OL45804W",
            rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/OL34184A"))),
            description = "The main character of Fantastic Mr. Fox is an extremely clever anthropomorphized fox named Mr. Fox.",
            coverIds = listOf(6498519, 8904777, 108274, 233884, 1119236, -1, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458),
            subjects = listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction"),
            error = null
        )

        val result = api.getWork("OL45804W")
        assertThat(result).isNotNull()
        assertThat(result.body()).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionGivesCorrectResult() = runTest {

        val json = FileReader.readResourceFromFile(this.javaClass.classLoader!!, "getEdition.json")

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expected = RawEdition(
            title = "Fantastic Mr. Fox",
            key = "/books/OL7353617M",
            authorRawKeys = listOf(RawKey("/authors/OL34184A")),
            isbn10 = listOf("0140328726"),
            isbn13 = listOf("9780140328721"),
            coverIds = listOf(8739161),
            workRawKeys = listOf(RawKey("/works/OL45804W")),
            error = null
        )

        val result = api.getEdition("OL7353617M")
        assertThat(result).isNotNull()
        assertThat(result.body()).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorGivesCorrectResult() = runTest {

        val json = FileReader.readResourceFromFile(this.javaClass.classLoader!!, "getAuthor.json")

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(json)
        mockWebServer.enqueue(expectedResponse)

        val expected = RawAuthor(
            wikipedia = "http://en.wikipedia.org/wiki/J._K._Rowling",
            name = "J. K. Rowling",
            birthDate = "31 July 1965",
            bio = "Joanne \"Jo\" Murray, OBE (née Rowling), better known under the pen name J. K. Rowling, is a British author best known as the creator of the Harry Potter fantasy series, the idea for which was conceived whilst on a train trip from Manchester to London in 1990. The Potter books have gained worldwide attention, won multiple awards, sold more than 400 million copies, and been the basis for a popular series of films.",
            key = "/authors/OL23919A",
            entityType = "person",
            photoIds = listOf(5543033, -1),
            error = null
        )

        val result = api.getAuthor("OL23919A")
        assertThat(result).isNotNull()
        assertThat(result.body()).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `real test`() = runTest {
        /*val realApi = Retrofit.Builder()
            .baseUrl("https://openlibrary.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryApi::class.java)
        val realRemoteDataSource = LibraryRemoteDataSourceImpl(realApi)
        val realRepository = LibraryRepositoryImpl(realRemoteDataSource)

        val res = realRepository.editionById("OL7353617M")
        println("## ${res.data}")*/
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}