package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow

/**
 * A user of the application, could be either
 * the [MainUser] or a [Friend]
 */
interface User : Keyed {

    val username: String

    val profilePictureUrl: String

    val phoneNumber: String?

    val userPreferences: UserPreferences

    val workPreferences: Flow<Map<String, WorkPreference>>

    val friends: List<User>

    val statistics: UserStatistics
}