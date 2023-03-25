package com.github.leuludyha.domain.repository

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    fun searchRemotely(query: String): Flow<PagingData<Work>>
    fun getWorkRemotely(workId: String): Flow<Result<Work>>
    fun getWorksByAuthorRemotely(authorId: String): Flow<Result<List<Work>>>
    fun getEditionsByWorkRemotely(workId: String): Flow<Result<List<Edition>>>
    fun getEditionRemotely(editionId: String): Flow<Result<Edition>>
    fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>>
    fun getAuthorRemotely(authorId: String): Flow<Result<Author>>
    suspend fun saveWorkLocally(work: Work): Unit
    suspend fun saveAuthorLocally(author: Author): Unit
    suspend fun saveEditionLocally(edition: Edition): Unit
    // TODO SAVE COVERS
    fun getWorkLocally(workId: String): Flow<Work>
    fun getAuthorLocally(authorId: String): Flow<Author>
    fun getEditionLocally(editionId: String): Flow<Edition>
    fun getCoverLocally(coverId: Long): Flow<Cover>

}