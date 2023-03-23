package com.github.leuludyha.data.db

import androidx.room.*

@Dao
interface WorkRemoteKeysDao {

    @Query("SELECT * FROM work_remote_keys WHERE workId = :workId")
    suspend fun getWorkRemoteKeys(workId: String): WorkRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workRemoteKeys : WorkRemoteKeys)

    @Query("DELETE FROM work_remote_keys")
    suspend fun deleteAllWorkRemoteKeys()
}
