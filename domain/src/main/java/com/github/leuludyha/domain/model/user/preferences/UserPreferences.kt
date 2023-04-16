package com.github.leuludyha.domain.model.user.preferences

open class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var workPreferences: MutableMap<String, WorkPreference> = mutableMapOf()
) {

    /**
     * Add a preference for a work to the list of work preference
     * in the user's preferences
     */
    fun addPreference(preference: WorkPreference) {
        this.workPreferences[preference.work.id] = preference
    }
}