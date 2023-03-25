package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.SearchRemotelyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides all the dependency injection related to the use cases.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideSearchUseCase(libraryRepository: LibraryRepository) =
       SearchRemotelyUseCase(libraryRepository)
}