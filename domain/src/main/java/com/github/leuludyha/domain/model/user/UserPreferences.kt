package com.github.leuludyha.domain.model.user

class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var preferencesByWorkId: MutableMap<String, WorkPreference> = mutableMapOf()
) {

    /**
     * Add a preference for a work to the list of work preference
     * in the user's preferences
     */
    fun addPreference(preference: WorkPreference) {
        this.preferencesByWorkId[preference.work.getId()] = preference
    }
}