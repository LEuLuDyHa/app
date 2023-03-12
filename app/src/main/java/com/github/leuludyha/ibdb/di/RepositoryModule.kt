package com.github.leuludyha.ibdb.di

import com.github.leuludyha.data.repository.LibraryRepositoryImpl
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.repository.LibraryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideOpenLibraryRepository(libraryRemoteDataSource: LibraryRemoteDataSource) : LibraryRepository =
        LibraryRepositoryImpl(libraryRemoteDataSource)
}