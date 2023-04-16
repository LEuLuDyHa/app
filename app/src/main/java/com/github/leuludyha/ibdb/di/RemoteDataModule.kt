package com.github.leuludyha.ibdb.di

import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to the remote data source.
 */
@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    fun provideLibraryRemoteDataSource(libraryApi: LibraryApi): LibraryRemoteDataSource =
        LibraryRemoteDataSourceImpl(libraryApi)

}