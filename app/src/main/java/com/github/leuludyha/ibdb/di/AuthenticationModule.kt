package com.github.leuludyha.ibdb.di

import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    fun provideAuthenticationContext(): AuthenticationContext = AuthenticationContext(
        User(
            Firebase.auth.currentUser?.displayName ?: "username",
            Firebase.auth.currentUser?.photoUrl.toString(),
            Mocks.userPreferences,
            UserStatistics(
                preferredAuthors = listOf(Mocks.authorGeorgeOrwell),
                preferredSubjects = listOf("Political Science"),
                preferredWorks = listOf(Mocks.workLaFermeDesAnimaux),
                averageNumberOfPages = 42,
            ),
            friends = listOf(Mocks.user)
        )
    )

}