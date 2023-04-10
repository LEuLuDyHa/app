package com.github.leuludyha.domain.repository

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    fun searchRemotely(query: String): Flow<PagingData<Work>>
    fun getWorkRemotely(workId: String): Flow<Result<Work>>
    fun getEditionRemotely(editionId: String): Flow<Result<Edition>>
    fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>>
    fun getAuthorRemotely(authorId: String): Flow<Result<Author>>
    suspend fun saveWorkLocally(work: Work)
    suspend fun saveAuthorLocally(author: Author)
    suspend fun saveEditionLocally(edition: Edition)

    // TODO SAVE COVERS
    fun getWorkLocally(workId: String): Flow<Work>
    fun getAuthorLocally(authorId: String): Flow<Author>
    fun getEditionLocally(editionId: String): Flow<Edition>
}