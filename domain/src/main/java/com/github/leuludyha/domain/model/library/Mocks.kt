package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference
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
        photos = flowOf(listOf(Cover(12919044L)))
    )

    val work: Work = Work(
        title = "1984",
        id = "12919044",
        editions = flowOf(listOf()),
        authors = flowOf(listOf(author)),
        covers = flowOf(listOf (Cover(12919044L))),
        subjects = flowOf(listOf("Censorship", "Futurology", "Surveillance"))
    )

    val userPreferences: UserPreferences = UserPreferences(
        mutableMapOf(
            Pair(
                work.id, WorkPreference(
                    work, WorkPreference.ReadingState.READING, false
                )
            )
        )
    )

    val user: User = User(
        username = "Mockentosh",
        preferences = userPreferences
    )
}