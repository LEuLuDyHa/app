package com.github.leuludyha.domain

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.*
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockDomainLibraryRepository: LibraryRepository {
    private val savedWorks: HashMap<String, Work> = hashMapOf()
    private val savedEditions: HashMap<String, Edition> = hashMapOf()
    private val savedAuthors: HashMap<String, Author> = hashMapOf()

    override fun searchRemotely(query: String): Flow<PagingData<Work>> {
        TODO("How to test PagingData???")
    }

    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        if (workId == mockWork.id)
            flowOf(Result.Success(mockWork))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> =
        if (editionId == mockEdition.id)
            flowOf(Result.Success(mockEdition))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> =
        if (isbn == mockEdition.isbn10 || isbn == mockEdition.isbn13)
            flowOf(Result.Success(mockEdition))
        else
            flowOf(Result.Error("isbn not found"))

    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        if (authorId == mockAuthor.id)
            flowOf(Result.Success(mockAuthor))
        else
            flowOf(Result.Error("id not found"))

    override suspend fun saveWorkLocally(work: Work) {
        savedWorks[work.id] = work
    }

    override suspend fun saveAuthorLocally(author: Author) {
        savedAuthors[author.id] = author
    }

    override suspend fun saveEditionLocally(edition: Edition) {
        savedEditions[edition.id] = edition
    }

    override fun getWorkLocally(workId: String): Flow<Work> =
        if (savedWorks[workId] == null)
            flowOf(savedWorks[workId]!!)
        else
            flowOf()

    override fun getAuthorLocally(authorId: String): Flow<Author> =
        if (savedAuthors[authorId] == null)
            flowOf(savedAuthors[authorId]!!)
        else
            flowOf()

    override fun getEditionLocally(editionId: String): Flow<Edition> =
        if (savedEditions[editionId] == null)
            flowOf(savedEditions[editionId]!!)
        else
            flowOf()

    companion object {
        val dumbWork = Work(
            title = "Fantastic Mr Fox",
            id = "OL45804W",
            authors = flowOf(listOf()),
            editions = flowOf(listOf()),
            covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{ Cover(it.toLong()) }),
            subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
        )

        val mockAuthor = Author(
        wikipedia = null,
        name = "Roald Dahl",
        birthDate = "13 September 1916",
        deathDate = "23 November 1990",
        //bio = "Roald Dahl was a British novelist, short story writer, and screenwriter.",
        id = "OL34184A",
        works = flowOf(listOf(dumbWork)),
        covers = flowOf ( listOf(9395323, 9395316, 9395314, 9395313, 6287214).map { Cover(it.toLong()) } ),
        )

        val mockEdition = Edition(
        title = "Fantastic Mr. Fox",
        id = "OL44247403M",
        isbn10 = null,
        isbn13 = "9780142418222",
        authors = flowOf(listOf(mockAuthor)),
        works = flowOf(listOf(dumbWork)),
        covers = flowOf(listOf(Cover(13269612))),
        )

        val mockWork = Work(
            title = "Fantastic Mr Fox",
            id = "OL45804W",
            authors = flowOf(listOf(mockAuthor)),
            editions = flowOf(listOf(mockEdition)),
            covers = flowOf(listOf(6498519, 8904777, 108274, 233884, 1119236, 10222599, 10482837, 3216657, 10519563, 10835922, 10835924, 10861366, 10883671, 8760472, 12583098, 10482548, 10831929, 10835926, 12333895, 12498647, 7682784, 12143357, 12781739, 3077458).map{Cover(it.toLong())}),
            subjects = flowOf(listOf("Animals", "Hunger", "Open Library Staff Picks", "Juvenile fiction", "Children's stories, English", "Foxes", "Fiction", "Zorros", "Ficci\u00f3n juvenil", "Tunnels", "Interviews", "Farmers", "Children's stories", "Rats", "Welsh Authors", "English Authors", "Thieves", "Tricksters", "Badgers", "Children's fiction", "Foxes, fiction", "Underground", "Renards", "Romans, nouvelles, etc. pour la jeunesse", "Children's literature", "Plays", "Children's plays", "Children's stories, Welsh", "Agriculteurs", "Large type books", "Fantasy fiction")),
        )
    }
}