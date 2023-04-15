package com.github.leuludyha.domain.model.user

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


class UserPreferences(
    /** Map WorkId -> WorkPreference */
    var workPreferences: MutableMap<String, WorkPreference> = mutableMapOf(),
    /** Whether this user prefers dark mode or not */
    val darkTheme: MutableState<Boolean> = mutableStateOf(false),
)