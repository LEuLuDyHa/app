package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val libraryRemoteDataSource: LibraryRemoteDataSource,
    private val libraryLocalDataSource: LibraryLocalDataSource
    ) : LibraryRepository
{
    /**
     * @return the result of an online search of the given [query].
     */
    override fun searchRemotely(query: String) =
        libraryRemoteDataSource.search(query)

    /**
     * @return the result of an online [Work] fetch query for the given work id.
     */
    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        libraryRemoteDataSource.getWork(workId)

    /**
     * @return the result of an online [Edition] fetch query for the given edition id.
     */
    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> =
        libraryRemoteDataSource.getEdition(editionId)

    /**
     * @return the result of an online [Edition] fetch query for the given isbn (either 13 or 10).
     */
    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> =
        libraryRemoteDataSource.getEditionByISBN(isbn)

    /**
     * @return the result of an online [Author] fetch query for the given author id.
     */
    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        libraryRemoteDataSource.getAuthor(authorId)

    override suspend fun saveWorkLocally(work: Work) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAuthorLocally(author: Author) {
        TODO("Not yet implemented")
    }

    override suspend fun saveEditionLocally(edition: Edition) {
        TODO("Not yet implemented")
    }

    /**
     * @return the result of a local [Work] work query for the given work id.
     */
    override fun getWorkLocally(workId: String): Flow<Work> =
        libraryLocalDataSource.getWork(workId)

    /**
     * @return the result of a local [Author] work query for the given author id.
     */
    override fun getAuthorLocally(authorId: String): Flow<Author> =
        libraryLocalDataSource.getAuthor(authorId)

    /**
     * @return the result of a local [Edition] work query for the given edition id.
     */
    override fun getEditionLocally(editionId: String): Flow<Edition> =
        libraryLocalDataSource.getEdition(editionId)
}