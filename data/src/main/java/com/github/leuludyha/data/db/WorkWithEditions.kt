package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [WorkEntity] and many [EditionEntity]
 */
data class WorkWithEditions(
    @Embedded val work: WorkEntity,
    @Relation(
        parentColumn = "workId",
        entityColumn = "editionId",
        associateBy = Junction(WorkEditionCrossRef::class)
    )
    val editions: List<EditionEntity>
)
