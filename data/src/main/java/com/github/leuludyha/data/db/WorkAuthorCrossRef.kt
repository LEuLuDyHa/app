package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["workId", "authorId"])
data class WorkAuthorCrossRef(
    val workId: String,
    val authorId: String,
)
