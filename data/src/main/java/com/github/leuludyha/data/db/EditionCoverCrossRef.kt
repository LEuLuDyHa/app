package com.github.leuludyha.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["editionId", "coverId"])
data class EditionCoverCrossRef(
    val editionId: String,
    val coverId: Long,
)
