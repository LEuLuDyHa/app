package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [WorkEntity] and many [CoverEntity]
 */
data class WorkWithCovers(
    @Embedded val work: WorkEntity,
    @Relation(
        parentColumn = "workId",
        entityColumn = "coverId",
        associateBy = Junction(WorkCoverCrossRef::class)
    )
    val covers: List<CoverEntity>
)
