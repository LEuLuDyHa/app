package com.github.leuludyha.domain.repository

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

/**
 * Provides indirect access to both the local and remote library data sources.
 */
interface LibraryRepository {
    /**
     * @return the result of an online search of the given [query].
     */
    fun searchRemotely(query: String): Flow<PagingData<Work>>
    /**
     * @return the result of an online [Work] fetch query for the given work id.
     */
    fun getWorkRemotely(workId: String): Flow<Result<Work>>
    /**
     * @return the result of an online [Edition] fetch query for the given edition id.
     */
    fun getEditionRemotely(editionId: String): Flow<Result<Edition>>
    /**
     * @return the result of an online [Edition] fetch query for the given isbn (either 13 or 10).
     */
    fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>>
    /**
     * @return the result of an online [Author] fetch query for the given author id.
     */
    fun getAuthorRemotely(authorId: String): Flow<Result<Author>>

    /**
     * Saves the given [Work] locally.
     */
    suspend fun saveLocally(work: Work)
    /**
     * Saves the given [Author] locally.
     */
    suspend fun saveLocally(author: Author)
    /**
     * Saves the given [Edition] locally.
     */
    suspend fun saveLocally(edition: Edition)
    /**
     * Saves the given [WorkPreference] locally.
     */
    suspend fun saveLocally(workPref: WorkPreference)

    // TODO SAVE COVERS

    /**
     * Deletes the given [Work] locally.
     */
    suspend fun deleteLocally(work: Work)
    /**
     * Deletes the given [Author] locally.
     */
    suspend fun deleteLocally(author: Author)
    /**
     * Deletes the given [Edition] locally.
     */
    suspend fun deleteLocally(edition: Edition)
    /**
     * Deletes the given [WorkPreference] locally.
     */
    suspend fun deleteLocally(workPref: WorkPreference)

    /**
     * @return the result of a local [Work] query for the given work id.
     */
    fun getWorkLocally(workId: String): Flow<Work>
    /**
     * @return the result of a local [Author] query for the given author id.
     */
    fun getAuthorLocally(authorId: String): Flow<Author>
    /**
     * @return the result of a local [Edition] query for the given edition id.
     */
    fun getEditionLocally(editionId: String): Flow<Edition>
    /**
     * @return the result of a local [Edition] query for the given edition isbn.
     * It can be either an ISBN10 or ISBN13.
     */
    fun getEditionByISBNLocally(isbn: String): Flow<Edition>
    /**
     * @return the result of a local [WorkPreference] query for the given work id.
     */
    fun getWorkPrefLocally(workId: String): Flow<WorkPreference>
    /**
     * @return the result of a local [WorkPreference] query for all work ids.
     */
    fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>>
}