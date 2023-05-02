package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

data class MainUser(
    var userId: String,
    override var username: String,
    override var profilePictureUrl: String,
    override var phoneNumber: String?,
    override val userPreferences: UserPreferences,
    override val workPreferences: Flow<Map<String, WorkPreference>>,
    override val statistics: UserStatistics,
    override val friends: List<User>,
    override val latitude: Double,
    override val longitude: Double

) : User {

    override fun Id(): String = userId

}