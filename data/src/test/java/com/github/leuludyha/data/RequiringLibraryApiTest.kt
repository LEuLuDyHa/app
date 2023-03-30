package com.github.leuludyha.data

import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.api.RawDocument
import com.github.leuludyha.data.api.RawEdition
import com.github.leuludyha.data.api.RawKey
import com.github.leuludyha.data.io.FileReader
import com.github.leuludyha.domain.model.library.*
import kotlinx.coroutines.flow.flowOf
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

open class RequiringLibraryApiTest {
    protected lateinit var libraryApi: LibraryApi
    protected lateinit var mockWebServer: MockWebServer

    protected lateinit var workJson: String
    protected lateinit var authorJson: String
    protected lateinit var editionJson: String
    protected lateinit var workEditionsJson: String
    protected lateinit var authorWorksJson: String

    protected lateinit var workResponse: MockResponse
    protected lateinit var authorResponse: MockResponse
    protected lateinit var editionResponse: MockResponse
    protected lateinit var workEditionsResponse: MockResponse
    protected lateinit var authorWorksResponse: MockResponse

    protected lateinit var mockWork: Work
    protected lateinit var mockAuthor: Author
    protected lateinit var mockEdition: Edition
    protected lateinit var mockRawDocument: RawDocument
    protected lateinit var mockRawEdition: RawEdition

    protected lateinit var mockWorkEditions: List<Edition>
    protected lateinit var mockAuthorWorks: List<Work>

    @Before
    fun initializeMockResponses() {

        editionJson = FileReader
            .readResourceFromFile(this.javaClass.classLoader!!, "getEdition.json")

        authorJson = FileReader
            .readResourceFromFile(this.javaClass.classLoader!!, "getAuthor.json")

        workJson = FileReader
            .readResourceFromFile(this.javaClass.classLoader!!, "getWork.json")

        workEditionsJson = FileReader
            .readResourceFromFile(this.javaClass.classLoader!!, "getWorkEditions.json")

        authorWorksJson = FileReader
            .readResourceFromFile(this.javaClass.classLoader!!, "getAuthorWorks.json")


        editionResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(editionJson)
        authorResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(authorJson)
        workResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(workJson)
        workEditionsResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(workEditionsJson)
        authorWorksResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(authorWorksJson)

        // To avoid infinite loop
        val dumbWork = Work(
            title = "Fantastic Mr Fox",
            id = "OL45804W",
            authors = flowOf(listOf()),
            editions = flowOf(listOf()),
            covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{ Cover(it.toLong()) }),
            subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
        )

        mockAuthor = Author(
            wikipedia = null,
            name = "Roald Dahl",
            birthDate = "13 September 1916",
            deathDate = "23 November 1990",
            //bio = "Roald Dahl was a British novelist, short story writer, and screenwriter.",
            id = "OL34184A",
            works = flowOf(listOf(dumbWork)),
            photos = flowOf ( listOf(9395323, 9395316, 9395314, 9395313, 6287214).map { Cover(it.toLong()) } ),
        )

        mockEdition = Edition(
            title = "Fantastic Mr. Fox",
            id = "OL44247403M",
            isbn10 = null,
            isbn13 = "9780142418222",
            authors = flowOf(listOf(mockAuthor)),
            works = flowOf(listOf(dumbWork)),
            covers = flowOf(listOf(Cover(13269612))),
        )

        mockWork = Work(
            title = "Fantastic Mr Fox",
            id = "OL45804W",
            authors = flowOf(listOf(mockAuthor)),
            editions = flowOf(listOf(mockEdition)),
            covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{ Cover(it.toLong()) }),
            subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
        )

        mockRawDocument = RawDocument(
            coverId = 0,
            title = "x",
            authorNames = listOf("x"),
            firstPublishYear = 0,
            key = "/works/OL45804W",
            authorIds = listOf("OL45804W"),
            editionIds = listOf("OL44247403M")
        )

        mockRawEdition = RawEdition(
            key = "/books/OL44247403M",
            title = "Fantastic Mr. Fox",
            isbn10 = null,
            isbn13 = listOf("9780142418222"),
            authorRawKeys = listOf(RawKey("/authors/OL34184A")),
            workRawKeys = listOf(RawKey("/works/OL45804W")),
            coverIds = listOf(-1, 1, 2),
            error = null,
        )

        mockWorkEditions = listOf(mockEdition)

        mockAuthorWorks = listOf(mockWork)
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        libraryApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibraryApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}