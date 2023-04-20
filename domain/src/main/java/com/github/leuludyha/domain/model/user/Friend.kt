package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics

/**
 * Represents a friend of the user. The friend interface
 */
data class Friend(
    val userId: String,
    override val username: String,
    override val phoneNumber: String?,
    override val profilePictureUrl: String,
    override val preferences: UserPreferences,
    override val friends: List<User>,
    override val statistics: UserStatistics,
    override val latitude: Double,
    override val longitude: Double

) : User {

    override fun Id(): String = userId
}