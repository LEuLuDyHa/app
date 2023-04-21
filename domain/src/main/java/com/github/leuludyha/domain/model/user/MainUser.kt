package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

data class MainUser(
    val userId: String,
    override var username: String,
    override val profilePictureUrl: String,
    override val phoneNumber: String?,
    override val userPreferences: UserPreferences,
    override val workPreferences: Flow<Map<String, WorkPreference>>,
    override val statistics: UserStatistics,
    override val friends: List<User>,
) : User {

    override fun Id(): String = userId

}