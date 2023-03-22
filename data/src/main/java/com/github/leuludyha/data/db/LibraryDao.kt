package com.github.leuludyha.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(work: WorkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(edition: EditionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: AuthorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cover: CoverEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkEditionCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: WorkCoverCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: AuthorCoverCrossRef)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: EditionAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: EditionCoverCrossRef)

    @Query("DELETE FROM works")
    suspend fun deleteAllWorks()

    @Query("DELETE FROM authors")
    suspend fun deleteAllAuthors()

    @Query("DELETE FROM Covers")
    suspend fun deleteAllCovers()

    @Query("DELETE FROM editions")
    suspend fun deleteAllEditions()
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWork(workId: String): Flow<WorkEntity>

    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEdition(editionId: String): Flow<EditionEntity>

    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthor(authorId: String): Flow<AuthorEntity>

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithAuthors(workId: String): Flow<WorkWithAuthors>

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithEditions(workId: String): Flow<WorkWithEditions>

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithCovers(workId: String): Flow<WorkWithCovers>

    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithWorks(authorId: String): Flow<AuthorWithWorks>

    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithCovers(authorId: String): Flow<AuthorWithCovers>
    
    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithAuthors(editionId: String): Flow<EditionWithAuthors>

    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithWorks(editionId: String): Flow<EditionWithWorks>

    @Transaction
    @Query("SELECT * FROM editions WHERE editionId LIKE :editionId")
    fun getEditionWithCovers(editionId: String): Flow<EditionWithCovers>
}
