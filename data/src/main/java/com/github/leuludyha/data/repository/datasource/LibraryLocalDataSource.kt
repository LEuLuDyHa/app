package com.github.leuludyha.data.repository.datasource

import android.graphics.Bitmap
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

/**
 * Provides access to the local library database.
 */
interface LibraryLocalDataSource {
    /**
     * Gets the [Work] with the given [workId] from the local database, if any.
     */
    fun getWork(workId: String): Flow<Work>
    /**
     * Gets the [Edition] with the given [editionId] from the local database, if any.
     */
    fun getEdition(editionId: String): Flow<Edition>
    /**
     * Gets the [Edition] with the given [isbn] from the local database, if any.
     */
    fun getEditionByISBN(isbn: String): Flow<Edition>
    /**
     * Gets the [Author] with the given [authorId] from the local database, if any.
     */
    fun getAuthor(authorId: String): Flow<Author>
    /**
     * Gets the [Cover] with the given [coverId] from the local database, if any.
     */
    fun getCover(coverId: Long): Flow<Cover>
    /**
     * Gets the [WorkPreference] attached to the [Work] with the given [workId] from the local database, if any.
     */
    fun getWorkPreference(workId: String): Flow<WorkPreference>
    /**
     * Gets all the [WorkPreference]s from the local database.
     */
    fun getAllWorkPreferences(): Flow<List<WorkPreference>>
    /**
     * Gets the bitmap associated to the given [Cover] for the given [CoverSize]
     */
    fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap>

    /**
     * Saves the given [Work] in the local database.
     */
    suspend fun save(work: Work)
    /**
     * Saves the given [Edition] in the local database.
     */
    suspend fun save(edition: Edition)
    /**
     * Saves the given [Author] in the local database.
     */
    suspend fun save(author: Author)
    /**
     * Saves the given [WorkPreference] in the local database.
     */
    suspend fun save(workPref: WorkPreference)

    /**
     * Deletes the given [Work] from the local database, if any.
     */
    suspend fun delete(work: Work)
    /**
     * Deletes the given [Edition] from the local database, if any.
     */
    suspend fun delete(edition: Edition)
    /**
     * Deletes the given [Author] from the local database, if any.
     */
    suspend fun delete(author: Author)
    /**
     * Deletes the given [WorkPreference] from the local database, if any.
     */
    suspend fun delete(workPref: WorkPreference)
}