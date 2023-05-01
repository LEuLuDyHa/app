package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

/**
 * Concrete implementation of the [LibraryRepository]
 */
class LibraryRepositoryImpl(
    private val libraryRemoteDataSource: LibraryRemoteDataSource,
    private val libraryLocalDataSource: LibraryLocalDataSource
    ) : LibraryRepository
{
    override fun searchRemotely(query: String) =
        libraryRemoteDataSource.search(query)
    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        libraryRemoteDataSource.getWork(workId)
    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> =
        libraryRemoteDataSource.getEdition(editionId)
    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> =
        libraryRemoteDataSource.getEditionByISBN(isbn)
    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        libraryRemoteDataSource.getAuthor(authorId)

    override suspend fun saveLocally(work: Work) =
        libraryLocalDataSource.save(work)
    override suspend fun saveLocally(author: Author) =
        libraryLocalDataSource.save(author)
    override suspend fun saveLocally(edition: Edition) =
        libraryLocalDataSource.save(edition)
    override suspend fun saveLocally(workPref: WorkPreference) =
        libraryLocalDataSource.save(workPref)

    override suspend fun deleteLocally(work: Work) =
        libraryLocalDataSource.delete(work)
    override suspend fun deleteLocally(author: Author) =
        libraryLocalDataSource.delete(author)
    override suspend fun deleteLocally(edition: Edition) =
        libraryLocalDataSource.delete(edition)
    override suspend fun deleteLocally(workPref: WorkPreference) =
        libraryLocalDataSource.delete(workPref)

    override fun getWorkLocally(workId: String): Flow<Work> =
        libraryLocalDataSource.getWork(workId)
    override fun getAuthorLocally(authorId: String): Flow<Author> =
        libraryLocalDataSource.getAuthor(authorId)
    override fun getEditionLocally(editionId: String): Flow<Edition> =
        libraryLocalDataSource.getEdition(editionId)
    override fun getEditionByISBNLocally(isbn: String): Flow<Edition> =
        libraryLocalDataSource.getEditionByISBN(isbn)
    override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> =
        libraryLocalDataSource.getWorkPreference(workId)
    override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> =
        libraryLocalDataSource.getAllWorkPreferences()

}