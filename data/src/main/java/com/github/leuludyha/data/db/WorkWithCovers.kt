package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkWithCovers(
    @Embedded val work: Work,
    @Relation(
        parentColumn = "workId",
        entityColumn = "coverId",
        associateBy = Junction(WorkCoverCrossRef::class)
    )
    val covers: List<Cover>
)
