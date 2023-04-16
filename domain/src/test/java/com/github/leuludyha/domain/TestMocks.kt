package com.github.leuludyha.domain

import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference

object TestMocks {

    val user1: User = User(
        "Camilla", null,
        UserPreferences(
            mutableMapOf(
                Pair(
                    Mocks.workLaFermeDesAnimaux.id, WorkPreference(
                        Mocks.workLaFermeDesAnimaux, WorkPreference.ReadingState.FINISHED, true
                    )
                )
            )
        ),
        UserStatistics(
            preferredSubjects = listOf("Fantasy", "Historical", "Political Science"),
            preferredAuthors = listOf(),
            preferredWorks = listOf(Mocks.workLaFermeDesAnimaux),
            averageNumberOfPages = 256,
        ),
        friends = listOf()
    )

    val user2: User = User(
        "Hector", null,
        UserPreferences(
            mutableMapOf(

            )
        ),
        UserStatistics(
            preferredSubjects = listOf(),
            preferredAuthors = listOf(Mocks.author),
            preferredWorks = listOf(Mocks.work1984),
            averageNumberOfPages = 14,
        ),
        friends = listOf(user1)
    )

    val user3: User = User(
        "Stella", null,
        UserPreferences(
            mutableMapOf(
                Pair(
                    Mocks.workLaFermeDesAnimaux.id, WorkPreference(
                        Mocks.workLaFermeDesAnimaux, WorkPreference.ReadingState.INTERESTED, true
                    )
                )
            )
        ),
        UserStatistics(
            preferredSubjects = listOf(),
            preferredAuthors = listOf(Mocks.author),
            preferredWorks = listOf(Mocks.work1984, Mocks.workLaFermeDesAnimaux),
            averageNumberOfPages = 1092,
        ),
        friends = listOf(user1)
    )
}