package com.github.leuludyha.data.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.github.leuludyha.data.FileReader
import com.github.leuludyha.data.RequiringLibraryRepositoryTest
import com.github.leuludyha.data.api.RawDocument
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.domain.useCase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
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

    private lateinit var workJson: String
    private lateinit var authorJson: String
    private lateinit var editionJson: String
    private lateinit var workEditionsJson: String

    private lateinit var workResponse: MockResponse
    private lateinit var authorResponse: MockResponse
    private lateinit var editionResponse: MockResponse
    private lateinit var workEditionsResponse: MockResponse

    private lateinit var mockWork: Work
    private lateinit var mockAuthor: Author
    private lateinit var mockEdition: Edition
    private lateinit var mockWorkEditions: List<Edition>

    @Before
    fun initializeMockResponses() {
        editionJson = FileReader
            .readResourceFromFile(InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("getEdition.json")
            )
        authorJson = FileReader
            .readResourceFromFile(InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("getAuthor.json")
            )
        workJson = FileReader
            .readResourceFromFile(InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("getWork.json")
            )
        workEditionsJson = FileReader
            .readResourceFromFile(InstrumentationRegistry
                .getInstrumentation()
                .context
                .assets
                .open("getWorkEditions.json")
            )

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

        mockAuthor = Author(
            wikipedia = null,
            name = "Roald Dahl",
            birthDate = "13 September 1916",
            deathDate = "23 November 1990",
            //bio = "Roald Dahl was a British novelist, short story writer, and screenwriter.",
            id = "OL34184A",
            photos = flowOf ( listOf(9395323, 9395316, 9395314, 9395313, 6287214).map { Cover(it.toLong()) } ),
        )

        // To avoid infinite loop
        val dumbWork = Work(
            title = "Fantastic Mr Fox",
            id = "OL45804W",
            authors = flowOf(listOf(mockAuthor)),
            editions = flowOf(),
            covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{Cover(it.toLong())}),
            subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
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
            covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{Cover(it.toLong())}),
            subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
        )

        mockWorkEditions = listOf(mockEdition)
    }
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
    fun getWorkRemotelyGivesCorrectResult() = runBlocking {

        mockWebServer.enqueue(workResponse)
        mockWebServer.enqueue(workEditionsResponse)
        mockWebServer.enqueue(authorResponse)

        val result = GetWorkRemotelyUseCase(libraryRepository)("OL45804W").first().data!!
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(mockWork)
        assertThat(result.id).isEqualTo(mockWork.id)
        assertThat(result.title).isEqualTo(mockWork.title)
        assertThat(result.editions.first()).isEqualTo(mockWork.editions.first())
        assertThat(result.authors.first()).isEqualTo(mockWork.authors.first())
        assertThat(result.covers.first()).isEqualTo(mockWork.covers.first())
        assertThat(result.subjects.first()).isEqualTo(mockWork.subjects.first())
    }

    @Test
    fun getAuthorRemotelyGivesCorrectResult() = runBlocking {
        mockWebServer.enqueue(authorResponse)

        val result = GetAuthorRemotelyUseCase(libraryRepository)("query").first().data!!

        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(mockAuthor)
        assertThat(result.name).isEqualTo(mockAuthor.name)
        //assertThat(result.bio).isEqualTo(expected.bio)
        assertThat(result.wikipedia).isEqualTo(mockAuthor.wikipedia)
        assertThat(result.deathDate).isEqualTo(mockAuthor.deathDate)
        assertThat(result.birthDate).isEqualTo(mockAuthor.birthDate)
        assertThat(result.photos.first()).isEqualTo(mockAuthor.photos.first())
    }

    @Test
    fun getEditionRemotelyGivesCorrectResult() = runBlocking {

        mockWebServer.enqueue(editionResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(workResponse)
        // Author for work
        mockWebServer.enqueue(authorResponse)

        val result = GetEditionRemotelyUseCase(libraryRepository)("OL7353617M").first().data!!
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(mockEdition)
        assertThat(result.id).isEqualTo(mockEdition.id)
        assertThat(result.title).isEqualTo(mockEdition.title)
        assertThat(result.isbn10).isEqualTo(mockEdition.isbn10)
        assertThat(result.isbn13).isEqualTo(mockEdition.isbn13)
        assertThat(result.covers.first()).isEqualTo(mockEdition.covers.first())
        assertThat(result.authors.first()).isEqualTo(mockEdition.authors.first())
        assertThat(result.works.first()).isEqualTo(mockEdition.works.first())
    }

    @Test
    fun getEditionByISBNRemotelyGivesCorrectResult() = runBlocking {

        mockWebServer.enqueue(editionResponse)
        mockWebServer.enqueue(authorResponse)
        mockWebServer.enqueue(workResponse)
        // Author for work
        mockWebServer.enqueue(authorResponse)

        val result = GetEditionByISBNRemotelyUseCase(libraryRepository)(mockEdition.isbn13!!).first().data!!
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(mockEdition)
        assertThat(result.id).isEqualTo(mockEdition.id)
        assertThat(result.title).isEqualTo(mockEdition.title)
        assertThat(result.isbn10).isEqualTo(mockEdition.isbn10)
        assertThat(result.isbn13).isEqualTo(mockEdition.isbn13)
        assertThat(result.covers.first()).isEqualTo(mockEdition.covers.first())
        assertThat(result.authors.first()).isEqualTo(mockEdition.authors.first())
        assertThat(result.works.first()).isEqualTo(mockEdition.works.first())
    }

//    @Test
//    fun real() = runBlocking {
//
//        val client = OkHttpClient.Builder()
//            .readTimeout(20, TimeUnit.SECONDS)
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//                .baseUrl("https://openlibrary.org")
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        val api =retrofit.create(LibraryApi::class.java)
//
//        mockWebServer.enqueue(editionResponse)
//        mockWebServer.enqueue(authorResponse)
//        mockWebServer.enqueue(workResponse)
//        // Author for work
//        mockWebServer.enqueue(authorResponse)
//
//        println("loL")
//
//        val remote = LibraryRemoteDataSourceImpl(api)
//        val repo = LibraryRepositoryImpl(remote, localDataSource)
//        val result = GetWorkRemotelyUseCase(repo)("OL45804W").first().data!!
//        println("## " + result.editions.first())
//    }
}