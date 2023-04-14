package com.github.leuludyha.domain.model.user

/**
 * Represents a friend of the user. The friend interface
 */
data class Friend(
    val userId: String,
    val username: String,
    val profilePictureUrl: String?,
) : User {
    override fun username(): String = username

    override fun profilePictureUrl(): String? = profilePictureUrl

    override fun Id(): String = userId
}