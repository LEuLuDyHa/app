package com.github.leuludyha.data.repository

import androidx.paging.PagingData
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.*
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val libraryRemoteDataSource: LibraryRemoteDataSource,
    private val libraryLocalDataSource: LibraryLocalDataSource
    ) : LibraryRepository
{
    override fun search(query: String) =
        libraryRemoteDataSource.search(query)

    override fun workById(workId: String): Flow<Result<Work>> =
        libraryRemoteDataSource.getWork(workId)
    override fun worksByAuthorId(authorId: String): Flow<Result<List<Work>>> =
        libraryRemoteDataSource.getWorksByAuthor(authorId)
    override fun editionsByWorkId(workId: String): Flow<Result<List<Edition>>> =
        libraryRemoteDataSource.getEditionsByWork(workId)
    override fun editionById(editionId: String): Flow<Result<Edition>> =
        libraryRemoteDataSource.getEdition(editionId)
    override fun editionByISBN(isbn: Long): Flow<Result<Edition>> =
        libraryRemoteDataSource.getEditionByISBN(isbn)
    override fun authorById(authorId: String): Flow<Result<Author>> =
        libraryRemoteDataSource.getAuthor(authorId)
    override fun getWorkLocally(workId: String): Flow<Work> =
        libraryLocalDataSource.getWork(workId)
    override fun getAuthorLocally(authorId: String): Flow<Author> =
        libraryLocalDataSource.getAuthor(authorId)
    override fun getEditionLocally(editionId: String): Flow<Edition> =
        libraryLocalDataSource.getEdition(editionId)
    override fun getCoverLocally(coverId: Long): Flow<Cover> =
        libraryLocalDataSource.getCover(coverId)
}