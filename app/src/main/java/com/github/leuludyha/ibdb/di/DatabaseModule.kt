package com.github.leuludyha.ibdb.di

import android.app.Application
import androidx.room.Room
import com.github.leuludyha.data.db.LibraryDao
import com.github.leuludyha.data.db.LibraryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to the database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabase(app: Application): LibraryDatabase =
        Room.databaseBuilder(app, LibraryDatabase::class.java, "library_db")
            .fallbackToDestructiveMigration()
            .build()
    @Provides
    fun provideLibraryDao(libraryDatabase: LibraryDatabase) : LibraryDao =
        libraryDatabase.libraryDao()
}