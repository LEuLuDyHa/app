package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.api.*
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModelResult
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LibraryRemoteDataSourceImpl(
    private val libraryApi: LibraryApi,
): LibraryRemoteDataSource {
    override suspend fun search(
        query: String,
        page: Int,
        resultsPerPage: Int,
    ): Result<List<Work>> =
        rawResponseToModelResult(libraryApi.search(query), libraryApi)

    override fun getWork(workId: String): Flow<Result<Work>> = flow {
        emit(rawResponseToModelResult(libraryApi.getWork(workId), libraryApi))
    }

    override fun getEdition(editionId: String): Flow<Result<Edition>> = flow {
        emit(rawResponseToModelResult(libraryApi.getEdition(editionId), libraryApi))
    }

    override fun getAuthor(authorId: String): Flow<Result<Author>> = flow {
        emit(rawResponseToModelResult(libraryApi.getAuthor(authorId), libraryApi))
    }

    override fun getWorksByAuthor(authorId: String): Flow<Result<List<Work>>> = flow {
        emit(rawResponseToModelResult(libraryApi.getWorksByAuthorId(authorId), libraryApi))
    }

    override fun getEditionsByWork(workId: String): Flow<Result<List<Edition>>> = flow {
        emit(rawResponseToModelResult(libraryApi.getEditionsByWorkId(workId), libraryApi))
    }

    override fun getEditionByISBN(isbn: Long): Flow<Result<Edition>> = flow {
        emit(rawResponseToModelResult(libraryApi.getEditionByISBN(isbn), libraryApi))
    }
}