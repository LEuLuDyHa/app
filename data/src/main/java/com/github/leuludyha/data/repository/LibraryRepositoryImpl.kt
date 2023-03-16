package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Result
import com.github.leuludyha.domain.model.Work
import com.github.leuludyha.domain.repository.LibraryRepository

class LibraryRepositoryImpl(private val libraryRemoteDataSource: LibraryRemoteDataSource) :
    LibraryRepository
{
    override suspend fun search(query: String) =
        libraryRemoteDataSource.search(query)
    override suspend fun workById(workId: String): Result<Work> =
        libraryRemoteDataSource.workById(workId)
    override suspend fun worksByAuthorId(authorId: String): Result<List<Work>> =
        libraryRemoteDataSource.worksByAuthorId(authorId)
    override suspend fun editionsByWorkId(workId: String): Result<List<Edition>> =
        libraryRemoteDataSource.editionsByWorkId(workId)
    override suspend fun editionById(editionId: String): Result<Edition> =
        libraryRemoteDataSource.editionById(editionId)
    override suspend fun editionByISBN(isbn: Long): Result<Edition> =
        libraryRemoteDataSource.editionByISBN(isbn)
    override suspend fun authorById(authorId: String): Result<Author> =
        libraryRemoteDataSource.authorById(authorId)
}