package com.github.leuludyha.data.db

import androidx.room.*

@Dao
interface LibraryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: WorkAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(work: WorkEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(author: AuthorEntity)

    @Transaction
    @Query("SELECT * FROM works WHERE workId LIKE :workId")
    fun getWorkWithAuthors(workId: String): WorkWithAuthors

    @Transaction
    @Query("SELECT * FROM authors WHERE authorId LIKE :authorId")
    fun getAuthorWithWorks(authorId: String): AuthorWithWorks
}