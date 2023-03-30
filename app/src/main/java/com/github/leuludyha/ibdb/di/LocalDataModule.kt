package com.github.leuludyha.ibdb.di

import com.github.leuludyha.data.db.LibraryDao
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to local data sources.
 */
@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun provideLocalDataSource(libraryDao: LibraryDao): LibraryLocalDataSource =
        LibraryLocalDataSourceImpl(libraryDao = libraryDao)
}