package com.github.leuludyha.domain.repository

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
    fun searchRemotely(query: String): Flow<PagingData<Work>>
    fun getWorkRemotely(workId: String): Flow<Result<Work>>
    fun getEditionRemotely(editionId: String): Flow<Result<Edition>>
    fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>>
    fun getAuthorRemotely(authorId: String): Flow<Result<Author>>
    suspend fun saveLocally(work: Work)
    suspend fun saveLocally(author: Author)
    suspend fun saveLocally(edition: Edition)
    suspend fun saveLocally(workPref: WorkPreference)

    // TODO SAVE COVERS

    suspend fun deleteLocally(work: Work)
    suspend fun deleteLocally(author: Author)
    suspend fun deleteLocally(edition: Edition)
    suspend fun deleteLocally(workPref: WorkPreference)

    fun getWorkLocally(workId: String): Flow<Work>
    fun getAuthorLocally(authorId: String): Flow<Author>
    fun getEditionLocally(editionId: String): Flow<Edition>
    fun getEditionByISBNLocally(isbn: String): Flow<Edition>
    fun getWorkPrefLocally(workId: String): Flow<WorkPreference>
}