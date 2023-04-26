package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.GetAllWorkPrefsLocallyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Singleton
    @Provides
    fun provideAuthenticationContext(libraryRepository: LibraryRepository): AuthenticationContext {
        return AuthenticationContext(
            MainUser(
                //TODO: Unfortunately cannot call Firebase directly from here (would fail if there were
                // no available network). I put mocks for now, but this should be replaced by
                // an access to the stored values in persistent memory in my opinion once this
                // option is available.
                Mocks.mainUser.userId,
                Mocks.mainUser.username,
                Mocks.mainUser.profilePictureUrl,
                Mocks.mainUser.phoneNumber,
                Mocks.userPreferences,
                GetAllWorkPrefsLocallyUseCase(libraryRepository = libraryRepository)
                    ().map { workPrefs -> workPrefs.associateBy { it.work.id } },
                UserStatistics(
                    preferredAuthors = listOf(Mocks.authorGeorgeOrwell),
                    preferredSubjects = listOf("Political Science"),
                    preferredWorks = listOf(Mocks.workLaFermeDesAnimaux),
                    averageNumberOfPages = 42,
                ),
                friends = listOf(Mocks.mainUser),
                0.0,
                0.0
            )
        )
    }

}