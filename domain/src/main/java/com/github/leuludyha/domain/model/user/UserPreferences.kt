package com.github.leuludyha.domain.model.user

class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var preferencesByWorkId: MutableMap<String, WorkPreference> = mutableMapOf()
)