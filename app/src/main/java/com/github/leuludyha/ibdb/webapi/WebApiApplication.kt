package com.github.leuludyha.ibdb.webapi

import android.app.Application
import com.github.leuludyha.ibdb.database.*
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

// TODO Modify this application to have a more general one
@HiltAndroidApp
open class WebApiApplication : Application() {
    open fun getBaseUrl() = "https://www.boredapi.com/api/"

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { ActivityDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ActivityRepository(database.activityDao()) }
}