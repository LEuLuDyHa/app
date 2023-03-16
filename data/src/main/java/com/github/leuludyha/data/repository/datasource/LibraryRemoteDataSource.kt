package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.data.api.Document
import com.github.leuludyha.domain.model.*

interface LibraryRemoteDataSource {
    suspend fun search(query: String): Result<List<Work>>
    suspend fun workById(workId: String): Result<Work>
    suspend fun worksByAuthorId(authorId: String): Result<List<Work>>
    suspend fun editionsByWorkId(workId: String): Result<List<Edition>>
    suspend fun editionById(editionId: String): Result<Edition>
    suspend fun editionByISBN(isbn: Long): Result<Edition>
    suspend fun authorById(authorId: String): Result<Author>
}