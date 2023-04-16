package com.github.leuludyha.domain.model.user

/**
 * Represents a friend of the user. The friend interface
 */
data class Friend(
    val userId: String,
    override val username: String,
    override val phoneNumber: String? = null,
    override val profilePictureUrl: String?,
) : User {

    override fun Id(): String = userId
}