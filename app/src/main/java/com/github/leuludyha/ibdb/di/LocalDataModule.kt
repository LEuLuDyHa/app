package com.github.leuludyha.ibdb.di

import android.content.Context
import com.github.leuludyha.data.db.LibraryDao
import com.github.leuludyha.data.repository.datasource.BitmapProvider
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to local data sources.
 */
@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun provideLocalDataSource(@ApplicationContext appContext: Context, bmpProvider: BitmapProvider, libraryDao: LibraryDao): LibraryLocalDataSource =
        LibraryLocalDataSourceImpl(appContext, bmpProvider = bmpProvider, libraryDao = libraryDao)
}