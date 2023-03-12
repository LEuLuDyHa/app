package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideSearchUseCase(libraryRepository: LibraryRepository) =
       SearchUseCase(libraryRepository)
}