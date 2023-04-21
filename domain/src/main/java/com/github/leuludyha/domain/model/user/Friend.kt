package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

/**
 * Represents a friend of the user. The friend interface
 */
data class Friend(
    val userId: String,
    override val username: String,
    override val phoneNumber: String?,
    override val profilePictureUrl: String,
    override val userPreferences: UserPreferences,
    override val workPreferences: Flow<Map<String, WorkPreference>>,
    override val friends: List<User>,
    override val statistics: UserStatistics,
) : User {

    override fun Id(): String = userId
}