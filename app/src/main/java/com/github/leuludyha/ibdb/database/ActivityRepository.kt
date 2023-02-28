package com.github.leuludyha.ibdb.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ActivityRepository(private val activityDao: ActivityDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allActivities: LiveData<List<ActivityItem>> = activityDao.getAll()

    suspend fun randomActivity(): ActivityItem? = activityDao.getRandom()

    suspend fun deleteAll() = activityDao.deleteAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(activity: ActivityItem) {
        activityDao.insert(activity)
    }
}