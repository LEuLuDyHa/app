package com.github.leuludyha.data.api

import com.github.leuludyha.data.RequiringLibraryApiTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RawAuthorWorksTest: RequiringLibraryApiTest() {

    val mockRawWork = RawWork(
        title = "Fantastic Mr Fox",
        key = "/works/OL45804W",
        rawAuthors = listOf(RawWork.RawWorkAuthor(RawKey("/authors/OL34184A"))),
        //description = "The main character of Fantastic Mr. Fox is an extremely clever anthropomorphized fox named Mr. Fox.",
        coverIds = listOf(6498519, 8904777, 108274, 233884, 1119236, -1, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458),
        subjects = listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction"),
        error = null
    )

    @Test
    fun fieldsAreProperlyGotten() {
        val authorWorks = RawAuthorWorks(
            links = RawAuthorLinks("/authors/OL34184A", "x", "x"),
            worksCount = 1,
            works = listOf(mockRawWork),
            error = "x"
        )

        assertThat(authorWorks.links).isEqualTo(RawAuthorLinks("/authors/OL34184A", "x", "x"))
        assertThat(authorWorks.worksCount).isEqualTo(1)
        assertThat(authorWorks.works).isEqualTo(listOf(mockRawWork))
        assertThat(authorWorks.error).isEqualTo("x")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelReturnsExpectedAuthor() { runBlocking {
        mockWebServer.enqueue(authorWorksResponse)

        val authorWorks = RawAuthorWorks(
            links = RawAuthorLinks("/authors/OL34184A", "x", "x"),
            worksCount = 1,
            works = listOf(mockRawWork),
            error = null
        )

        val res = authorWorks.toModel(libraryApi)!!

        assertThat(res).isEqualTo(listOf(mockWork))
    }}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun toModelFailsOnError() { runBlocking {
        mockWebServer.enqueue(authorWorksResponse)

        val authorWorks = RawAuthorWorks(
            links = RawAuthorLinks("/authors/OL34184A", "x", "x"),
            worksCount = 1,
            works = listOf(mockRawWork),
            error = "x"
        )

        assertThat(authorWorks.toModel(libraryApi)).isNull()
    }}
}