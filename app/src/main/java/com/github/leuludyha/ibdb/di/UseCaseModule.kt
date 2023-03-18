package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.library.SearchUseCase
import com.github.leuludyha.domain.useCase.auth.signin.FirebaseSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.OneTapSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
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

    @Provides
    fun provideSignInUseCases(authRepository: AuthRepository) =
        SignInUseCases(
            oneTapSignInUseCase = OneTapSignInUseCase(authRepository),
            firebaseSignInUseCase = FirebaseSignInUseCase(authRepository)
        )

}