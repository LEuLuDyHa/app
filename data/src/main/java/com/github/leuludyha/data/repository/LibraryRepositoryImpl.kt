package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Result
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

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

    override suspend fun saveWorkLocally(work: Work) {
        TODO("Not yet implemented")
    }

    override suspend fun saveAuthorLocally(author: Author) {
        TODO("Not yet implemented")
    }

    override suspend fun saveEditionLocally(edition: Edition) {
        TODO("Not yet implemented")
    }

    override fun getWorkLocally(workId: String): Flow<Work> =
        libraryLocalDataSource.getWork(workId)
    override fun getAuthorLocally(authorId: String): Flow<Author> =
        libraryLocalDataSource.getAuthor(authorId)
    override fun getEditionLocally(editionId: String): Flow<Edition> =
        libraryLocalDataSource.getEdition(editionId)
}