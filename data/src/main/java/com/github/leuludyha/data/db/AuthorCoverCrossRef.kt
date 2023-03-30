package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["authorId", "coverId"])
data class AuthorCoverCrossRef(
    val authorId: String,
    val coverId: Long,
)
