package com.github.leuludyha.domain.model.user

data class User(
    val username: String,
    val profilePictureUrl: String?,
    val preferences: UserPreferences,
)