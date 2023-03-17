package com.github.leuludyha.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorks(movies: List<Work>)

    @Query("SELECT * FROM works")
    fun getAllWorks(): PagingSource<Int, Work>

    @Query("SELECT * FROM works WHERE id = :id")
    fun getWorkById(id: String): Flow<Work>

    @Query("DELETE FROM works")
    suspend fun deleteAllWorks()
}