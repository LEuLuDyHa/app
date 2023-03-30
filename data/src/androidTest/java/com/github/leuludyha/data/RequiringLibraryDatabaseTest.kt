package com.github.leuludyha.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.leuludyha.data.db.LibraryDao
import com.github.leuludyha.data.db.LibraryDatabase
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryLocalDataSourceImpl
import org.junit.After
import org.junit.Before
import java.io.IOException

open class RequiringLibraryDatabaseTest {
    protected lateinit var db: LibraryDatabase
    protected lateinit var localDataSource: LibraryLocalDataSource
    protected lateinit var libraryDao: LibraryDao

    @Before
    fun createDatabase() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        ).allowMainThreadQueries().build()
        libraryDao = db.libraryDao()
        localDataSource = LibraryLocalDataSourceImpl(libraryDao)

        TestUtils.populateDatabase(libraryDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
        db.close()
    }
}