package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.auth.signin.FirebaseSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.OneTapSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
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

    @Provides
    fun provideSignInUseCases(authRepository: AuthRepository) =
        SignInUseCases(
            oneTapSignInUseCase = OneTapSignInUseCase(authRepository),
            firebaseSignInUseCase = FirebaseSignInUseCase(authRepository)
        )

}