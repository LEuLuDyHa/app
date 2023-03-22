package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkWithEditions(
    @Embedded val work: Work,
    @Relation(
        parentColumn = "workId",
        entityColumn = "editionId",
        associateBy = Junction(WorkEditionCrossRef::class)
    )
    val editions: List<Edition>
)
