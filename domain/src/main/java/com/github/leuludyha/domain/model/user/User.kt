package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics

data class User(
    val username: String,
    val profilePictureUrl: String?,
    val preferences: UserPreferences,
    val statistics: UserStatistics,
    val friends: List<User>
) {
    /** @return The list of works which are in the user's reading list */
    fun getWorksInReadingList(): Iterable<Work> {
        return this.preferences.workPreferences.values.map { it.work }
    }
}