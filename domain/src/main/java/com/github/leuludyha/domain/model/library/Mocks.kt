package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.authentication.AuthenticationContext
import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.flowOf

/** A mock work we can use to preview stuff or test */
object Mocks {
    val author: Author = Author(
        id = "9160343",
        name = "George Orwell",
        birthDate = null,
        deathDate = null,
        wikipedia = null,
        works = flowOf(listOf()),
        covers = flowOf(listOf(Cover(12919044L)))
    )

    val work1984: Work = Work(
        title = "1984",
        id = "OL1168083W",
        editions = flowOf(listOf()),
        authors = flowOf(listOf(author)),
        covers = flowOf(listOf(Cover(12725451L))),
        subjects = flowOf(listOf("Censorship", "Futurology", "Surveillance"))
    )

    val workLaFermeDesAnimaux: Work = Work(
        title = "La Ferme des Animaux",
        id = "OL26038920W",
        editions = flowOf(listOf()),
        authors = flowOf(listOf(author)),
        covers = flowOf(listOf(Cover(13147152L))),
        subjects = flowOf(listOf("Fiction", "Historical", "Political Science"))
    )

    val userPreferences: UserPreferences = UserPreferences(
        mutableMapOf(
            Pair(work1984.id, WorkPreference(work1984, WorkPreference.ReadingState.READING, false)),
            Pair(
                workLaFermeDesAnimaux.id,
                WorkPreference(workLaFermeDesAnimaux, WorkPreference.ReadingState.FINISHED, true)
            )
        )
    )

    val user: User = User(
        username = "Mockentosh",
        preferences = userPreferences,
        profilePictureUrl = null,
        statistics = UserStatistics(
            preferredWorks = listOf(work1984),
            preferredSubjects = listOf("Censorship"),
            preferredAuthors = listOf(author),
            averageNumberOfPages = 42
        ),
        friends = listOf()
    )

    val authContext: AuthenticationContext = AuthenticationContext(user)
}