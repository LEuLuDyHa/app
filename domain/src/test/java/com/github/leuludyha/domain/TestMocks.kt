package com.github.leuludyha.domain

import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.user.MainUser
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import java.util.*

object TestMocks {

    val user1: User = MainUser(
        UUID.randomUUID().toString(),
        "Camilla", "",
        null,
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
        friends = listOf(),
        latitude = 0.0,
        longitude = 0.0
    )

    val user2: User = MainUser(
        UUID.randomUUID().toString(),
        "Hector", "",
        null,
        UserPreferences(
            mutableMapOf(

            )
        ),
        UserStatistics(
            preferredSubjects = listOf(),
            preferredAuthors = listOf(Mocks.authorGeorgeOrwell),
            preferredWorks = listOf(Mocks.work1984),
            averageNumberOfPages = 14,
        ),
        friends = listOf(user1),
        latitude = 0.0,
        longitude = 0.0
    )

    val user3: User = MainUser(
        UUID.randomUUID().toString(),
        "Stella", "", null,
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
            preferredAuthors = listOf(Mocks.authorGeorgeOrwell),
            preferredWorks = listOf(Mocks.work1984, Mocks.workLaFermeDesAnimaux),
            averageNumberOfPages = 1092,
        ),
        friends = listOf(user1),
        latitude = 0.0,
        longitude = 0.0
    )
}