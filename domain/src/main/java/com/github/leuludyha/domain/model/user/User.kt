package com.github.leuludyha.domain.model.user

data class User(
    var username: String,
    val profilePictureUrl: String?,
    val preferences: UserPreferences,
)