package com.github.leuludyha.domain.model.user.preferences

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class UserPreferences(
    /** Whether this user prefers dark mode or not */
    val darkTheme: MutableState<Boolean> = mutableStateOf(false),
)