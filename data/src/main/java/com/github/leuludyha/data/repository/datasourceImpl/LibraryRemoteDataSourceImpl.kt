package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.api.*
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModelResult
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.*


class LibraryRemoteDataSourceImpl(
    private val libraryApi: LibraryApi,
): LibraryRemoteDataSource {
    override suspend fun search(
        query: String,
        page: Int,
        resultsPerPage: Int,
    ): Result<List<Work>> =
        rawResponseToModelResult(libraryApi.search(query), libraryApi)
    override suspend fun workById(workId: String): Result<Work> =
        rawResponseToModelResult(libraryApi.workById(workId), libraryApi)
    override suspend fun worksByAuthorId(authorId: String): Result<List<Work>> =
        rawResponseToModelResult(libraryApi.worksByAuthorId(authorId), libraryApi)
    override suspend fun editionsByWorkId(workId: String): Result<List<Edition>> =
        rawResponseToModelResult(libraryApi.editionsByWorkId(workId), libraryApi)
    override suspend fun editionById(editionId: String): Result<Edition> =
        rawResponseToModelResult(libraryApi.editionById(editionId), libraryApi)
    override suspend fun editionByISBN(isbn: Long): Result<Edition> =
        rawResponseToModelResult(libraryApi.editionByISBN(isbn), libraryApi)
    override suspend fun authorById(authorId: String): Result<Author> =
        rawResponseToModelResult(libraryApi.authorById(authorId), libraryApi)
}