package com.github.leuludyha.ibdb.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DAOs is the interface between the Database and the user. It allows to facilitate operations
 * on the database by abstracting the SQL.
 */
@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity_table")
    fun getAll(): LiveData<List<ActivityItem>>

    @Query("SELECT * FROM activity_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandom(): ActivityItem?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(activity: ActivityItem)

    @Insert
    fun insertAll(vararg activities: ActivityItem)

    @Delete
    suspend fun delete(activity: ActivityItem) // Delete the given activity

    @Query("DELETE FROM activity_table") //
    suspend fun deleteAll() // Delete all stored activities
}