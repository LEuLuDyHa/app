package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.repository.AuthRepository
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.repository.UserRepository
import com.github.leuludyha.domain.useCase.*
import com.github.leuludyha.domain.useCase.auth.signin.FirebaseSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.OneTapSignInUseCase
import com.github.leuludyha.domain.useCase.auth.signin.SignInUseCases
import com.github.leuludyha.domain.useCase.users.GetNearbyUsersUseCase
import com.github.leuludyha.domain.useCase.users.GetUserFromPhoneNumberUseCase
import com.github.leuludyha.domain.util.NetworkProvider
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
    fun provideSearchUseCase(libraryRepository: LibraryRepository, networkProvider: NetworkProvider) =
        SearchRemotelyUseCase(libraryRepository, networkProvider)

    @Provides
    fun provideGetAllWorkPrefsLocallyUseCase(libraryRepository: LibraryRepository) =
        GetAllWorkPrefsLocallyUseCase(libraryRepository)

    @Provides
    fun provideSaveWorkPrefLocallyUseCase(libraryRepository: LibraryRepository) =
        SaveWorkPrefLocallyUseCase(libraryRepository)

    @Provides
    fun provideDeleteWorkPrefLocallyUseCase(libraryRepository: LibraryRepository) =
        DeleteWorkPrefLocallyUseCase(libraryRepository)

    @Provides
    fun provideSignInUseCases(authRepository: AuthRepository) =
        SignInUseCases(
            oneTapSignInUseCase = OneTapSignInUseCase(authRepository),
            firebaseSignInUseCase = FirebaseSignInUseCase(authRepository)
        )

    @Provides
    fun getWorkByIdUseCase(libraryRepository: LibraryRepository, networkProvider: NetworkProvider) =
        GetWorkRemotelyUseCase(libraryRepository, networkProvider)

    @Provides
    fun getAuthorByIdUseCase(libraryRepository: LibraryRepository, networkProvider: NetworkProvider) =
        GetAuthorRemotelyUseCase(libraryRepository, networkProvider)

    @Provides
    fun getUserFromPhoneNumberUseCase(userRepository: UserRepository) =
        GetUserFromPhoneNumberUseCase(userRepository)

    @Provides
    fun getNearbyUsersUseCase(userRepository: UserRepository) =
        GetNearbyUsersUseCase(userRepository)
}