package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics

data class User(
    val username: String,
    val profilePictureUrl: String?,
    val preferences: UserPreferences,
    val statistics: UserStatistics,
    val friends: List<User>
)