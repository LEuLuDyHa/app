package com.github.leuludyha.ibdb.model

import java.util.*

data class User(
    val id: String,
    val username: String,
    // Same remark as in books, represent rest of the data which might or might not be
    // fetched
    val info: Optional<UserInfo>,
)

data class UserInfo(
    val profilePictureUrl: String,
)
