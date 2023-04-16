package com.github.leuludyha.domain.model.user

data class MainUser(
    val userId: String,
    override var username: String,
    override val profilePictureUrl: String?,
    val preferences: UserPreferences,
) : User {

    override fun Id(): String = userId

}