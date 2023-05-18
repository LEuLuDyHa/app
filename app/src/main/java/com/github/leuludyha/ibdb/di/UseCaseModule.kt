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
    fun getWorkByIdUseCase(libraryRepository: LibraryRepository) =
        GetWorkRemotelyUseCase(libraryRepository)

    @Provides
    fun getAuthorByIdUseCase(libraryRepository: LibraryRepository) =
        GetAuthorRemotelyUseCase(libraryRepository)

    @Provides
    fun getUserFromPhoneNumberUseCase(userRepository: UserRepository) =
        GetUserFromPhoneNumberUseCase(userRepository)

    @Provides
    fun getNearbyUsersUseCase(userRepository: UserRepository) =
        GetNearbyUsersUseCase(userRepository)

    @Provides
    fun getSerializeWorkUseCase(libraryRepository: LibraryRepository) =
        SerializeWorkUseCase(libraryRepository)

    @Provides
    fun getSaveWorkLocallyUseCase(libraryRepository: LibraryRepository) =
        SaveWorkLocallyUseCase(libraryRepository)

}