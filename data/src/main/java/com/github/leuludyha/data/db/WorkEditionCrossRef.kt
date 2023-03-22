package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["workId", "editionId"])
data class WorkEditionCrossRef(
    val workId: String,
    val editionId: String,
)
