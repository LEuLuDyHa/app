package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["editionId", "authorId"])
data class EditionAuthorCrossRef(
    val editionId: String,
    val authorId: String,
)
