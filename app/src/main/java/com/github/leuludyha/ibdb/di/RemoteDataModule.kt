package com.github.leuludyha.ibdb.di

import com.github.leuludyha.data.api.SearchApi
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.data.repository.datasourceImpl.LibraryRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    fun provideSearchRemoteDataSource(searchApi: SearchApi) : LibraryRemoteDataSource =
        LibraryRemoteDataSourceImpl(searchApi)
}