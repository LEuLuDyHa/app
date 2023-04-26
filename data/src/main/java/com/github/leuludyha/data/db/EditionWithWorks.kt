package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [EditionEntity] and many [WorkEntity]
 */
data class EditionWithWorks (
    @Embedded val edition: EditionEntity,
    @Relation(
        parentColumn = "editionId",
        entityColumn = "workId",
        associateBy = Junction(WorkEditionCrossRef::class)
    )
    val works: List<WorkEntity>
)