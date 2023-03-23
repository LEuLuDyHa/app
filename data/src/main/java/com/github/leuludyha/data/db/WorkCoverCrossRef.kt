package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["workId", "coverId"])
data class WorkCoverCrossRef(
    val workId: String,
    val coverId: Long,
)
