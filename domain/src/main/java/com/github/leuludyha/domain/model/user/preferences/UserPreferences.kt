package com.github.leuludyha.domain.model.user.preferences

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var workPreferences: MutableMap<String, WorkPreference> = mutableMapOf(),
    /** Whether this user prefers dark mode or not */
    val darkTheme: MutableState<Boolean> = mutableStateOf(false),
) {

    /**
     * Add a preference for a work to the list of work preference
     * in the user's preferences
     */
    fun addPreference(preference: WorkPreference) {
        this.workPreferences[preference.work.id] = preference
    }

    /**
     * Find all works in the user's reading list
     */
    fun getWorksInReadingList() = this.workPreferences.values.map { it.work }.toList()
}