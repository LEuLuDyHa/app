package com.github.leuludyha.domain.model.user.preferences

open class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var workPreferences: MutableMap<String, WorkPreference> = mutableMapOf()
)