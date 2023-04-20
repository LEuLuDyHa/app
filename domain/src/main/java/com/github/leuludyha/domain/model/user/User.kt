package com.github.leuludyha.domain.model.user

import com.github.leuludyha.domain.model.interfaces.Keyed
import com.github.leuludyha.domain.model.user.preferences.UserPreferences
import com.github.leuludyha.domain.model.user.preferences.UserStatistics

/**
 * A user of the application, could be either
 * the [MainUser] or a [Friend]
 */
interface User : Keyed {

    val username: String

    val profilePictureUrl: String

    val phoneNumber: String?

    val preferences: UserPreferences

    val friends: List<User>

    val statistics: UserStatistics

    val latitude: Double

    val longitude: Double
}