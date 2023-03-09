package com.github.leuludyha.ibdb.di

import com.github.leuludyha.data.repository.OpenLibraryRepositoryImpl
import com.github.leuludyha.data.repository.datasource.SearchRemoteDataSource
import com.github.leuludyha.domain.repository.OpenLibraryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideOpenLibraryRepository(searchRemoteDataSource: SearchRemoteDataSource) : OpenLibraryRepository =
        OpenLibraryRepositoryImpl(searchRemoteDataSource)
}