package com.github.leuludyha.domain.model.library

import com.github.leuludyha.domain.model.user.User
import com.github.leuludyha.domain.model.user.UserPreferences
import com.github.leuludyha.domain.model.user.WorkPreference

/** A mock work we can use to preview stuff or test */
object Mocks {
    val author: Author = Author(
        "9160343", null,
        name = "George Orwell",
        null, null, null
    )

    val work1: Work = Work(
        title = "1984",
        id = "12919044",
        fetchAuthors = suspend { listOf(author) },
        coverUrls = listOf { "https://covers.openlibrary.org/b/id/12919044-L.jpg" },
        subjects = listOf("Censorship", "Futurology", "Surveillance")
    )

    val work2: Work = Work(
        title = "La Ferme des Animaux",
        id = "13147152",
        fetchAuthors = suspend { listOf(author) },
        coverUrls = listOf { "https://covers.openlibrary.org/b/id/13147152-L.jpg" },
        subjects = listOf("Fiction", "Historical", "Political Science")
    )

    val userPreferences: UserPreferences = UserPreferences(
        mutableMapOf(
            Pair(
                work1.getId(), WorkPreference(
                    work1, WorkPreference.ReadingState.READING, false
                )
            )
        )
    )

    val user: User = User(
        username = "Mockentosh",
        preferences = userPreferences
    )
}