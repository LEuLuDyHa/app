package com.github.leuludyha.domain.model.user

data class MainUser(
    val userId: String,
    var username: String,
    val profilePictureUrl: String?,
    val preferences: UserPreferences,
) : User {
    override fun username(): String = username

    override fun profilePictureUrl(): String? = profilePictureUrl

    override fun Id(): String = userId

}