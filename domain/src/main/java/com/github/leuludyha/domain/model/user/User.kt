package com.github.leuludyha.domain.model.user

data class User(
    val username: String,
    val preferences: UserPreferences,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val friends: List<User> = emptyList() //or List<String> ?
    // val profilePicUrl: String
)