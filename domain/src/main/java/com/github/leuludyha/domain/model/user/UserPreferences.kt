package com.github.leuludyha.domain.model.user

class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var workPreferences: MutableMap<String, WorkPreference> = mutableMapOf()
)