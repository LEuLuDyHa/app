package com.github.leuludyha.domain

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockLibraryRepositoryImpl: LibraryRepository {
    private val savedWorks: HashMap<String, Work> = hashMapOf()
    private val savedEditions: HashMap<String, Edition> = hashMapOf()
    private val savedAuthors: HashMap<String, Author> = hashMapOf()
    private val savedWorkPrefs: HashMap<String, WorkPreference> = hashMapOf()

    override fun searchRemotely(query: String): Flow<PagingData<Work>> {
        return flowOf(PagingData.from(listOf(workMrFox)))
        // TODO("How to test PagingData???")
    }

    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        if (workId == workMrFox.id)
            flowOf(Result.Success(workMrFox))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> =
        if (editionId == editionMrFox.id)
            flowOf(Result.Success(editionMrFox))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> =
        if (isbn == editionMrFox.isbn10 || isbn == editionMrFox.isbn13)
            flowOf(Result.Success(editionMrFox))
        else
            flowOf(Result.Error("isbn not found"))

    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        if (authorId == authorRoaldDahl.id)
            flowOf(Result.Success(authorRoaldDahl))
        else
            flowOf(Result.Error("id not found"))

    override suspend fun saveLocally(work: Work) {
        savedWorks[work.id] = work
    }

    override suspend fun saveLocally(author: Author) {
        savedAuthors[author.id] = author
    }

    override suspend fun saveLocally(edition: Edition) {
        savedEditions[edition.id] = edition
    }

    override suspend fun saveLocally(workPref: WorkPreference) {
        savedWorkPrefs[workPref.work.id] = workPref
    }

    override suspend fun deleteLocally(work: Work) {
        savedWorks.remove(work.id)
    }

    override suspend fun deleteLocally(author: Author) {
        savedAuthors.remove(author.id)
    }

    override suspend fun deleteLocally(edition: Edition) {
        savedEditions.remove(edition.id)
    }

    override suspend fun deleteLocally(workPref: WorkPreference) {
        savedWorkPrefs.remove(workPref.work.id)
    }

    override fun getWorkLocally(workId: String): Flow<Work> =
        if (savedWorks[workId] != null)
            flowOf(savedWorks[workId]!!)
        else
            flowOf()

    override fun getAuthorLocally(authorId: String): Flow<Author> =
        if (savedAuthors[authorId] != null)
            flowOf(savedAuthors[authorId]!!)
        else
            flowOf()

    override fun getEditionLocally(editionId: String): Flow<Edition> =
        if (savedEditions[editionId] != null)
            flowOf(savedEditions[editionId]!!)
        else
            flowOf()

    override fun getEditionByISBNLocally(isbn: String): Flow<Edition> {
        val editions = savedEditions.values.filter { it.isbn10 == isbn || it.isbn13 == isbn }
        return if(editions.firstOrNull() == null)
            flowOf()
        else
            flowOf(editions.first())
    }

    override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> =
        if (savedWorkPrefs[workId] != null)
            flowOf(savedWorkPrefs[workId]!!)
        else
            flowOf()

}