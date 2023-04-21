package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics

data class MainUser(
    val userId: String,
    override var username: String,
    override val profilePictureUrl: String,
    override val phoneNumber: String?,
    override val preferences: UserPreferences,
    override val statistics: UserStatistics,
    override val friends: List<User>,
    override val latitude: Double,
    override val longitude: Double

) : User {

    override fun Id(): String = userId

}