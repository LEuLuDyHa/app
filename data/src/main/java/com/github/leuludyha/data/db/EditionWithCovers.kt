package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [EditionEntity] and many [CoverEntity]
 */
data class EditionWithCovers (
    @Embedded val edition: EditionEntity,
    @Relation(
        parentColumn = "editionId",
        entityColumn = "coverId",
        associateBy = Junction(EditionCoverCrossRef::class)
    )
    val covers: List<CoverEntity>
)