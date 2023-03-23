package com.github.leuludyha.domain.repository

import com.github.leuludyha.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    suspend fun search(query: String): Result<List<Work>>
    fun workById(workId: String): Flow<Result<Work>>
    fun worksByAuthorId(authorId: String): Flow<Result<List<Work>>>
    fun editionsByWorkId(workId: String): Flow<Result<List<Edition>>>
    fun editionById(editionId: String): Flow<Result<Edition>>
    fun editionByISBN(isbn: Long): Flow<Result<Edition>>
    fun authorById(authorId: String): Flow<Result<Author>>
    fun getWorkLocally(workId: String): Flow<Work>
    fun getAuthorLocally(authorId: String): Flow<Author>
    fun getEditionLocally(editionId: String): Flow<Edition>
    fun getCoverLocally(coverId: Long): Flow<Cover>

}