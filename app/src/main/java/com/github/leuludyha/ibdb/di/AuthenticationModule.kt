package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.GetAllWorkPrefsLocallyUseCase
import com.github.leuludyha.ibdb.util.Constant
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
                //TODO: Unfortunately cannot call Firebase directly from here (would fail if there was
                // no available network). A call to sharedPreferences to get local data needs the app context,
                // which is hard to get from this singleton and would fail the first time the app is opened.
                // I am struggling to find a cleaner implementation of this while keeping the authentication context injection.
                // To clear things, right now this is initialized in a stupid state as seen below,
                // and gets modified during login into google, which stores new values both persistently in memory
                // and also into the singleton.
                // To test all this properly, 3 elements must be taken into account (and their combinations)
                // network connection, first time login, cached values
                // Current implementation is messy in the code, but at least "works" (doesn't crash) in every case and
                // only has a weird behavior when the cached is cleared, it is the first login and there is no network connection.
                // So I am open to ideas if anyone finds something better.
                Constant.USER_NOT_FOUND,
                Constant.USER_NOT_FOUND,
                Constant.USER_NOT_FOUND,
                Constant.USER_NOT_FOUND,
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