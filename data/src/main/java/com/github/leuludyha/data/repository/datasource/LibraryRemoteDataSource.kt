package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LibraryRemoteDataSource {
    suspend fun search(
        query: String,
        page: Int = 1,
        resultsPerPage: Int = 20
    ): Result<List<Work>>
    fun getWork(workId: String): Flow<Result<Work>>
    fun getEdition(editionId: String): Flow<Result<Edition>>
    fun getAuthor(authorId: String): Flow<Result<Author>>
    fun getWorksByAuthor(authorId: String): Flow<Result<List<Work>>>
    fun getEditionsByWork(workId: String): Flow<Result<List<Edition>>>
    fun getEditionByISBN(isbn: Long): Flow<Result<Edition>>
}