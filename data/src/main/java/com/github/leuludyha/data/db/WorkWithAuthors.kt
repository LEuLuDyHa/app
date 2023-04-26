package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [WorkEntity] and many [AuthorEntity]
 */
data class WorkWithAuthors(
    @Embedded val work: WorkEntity,
    @Relation(
        parentColumn = "workId",
        entityColumn = "authorId",
        associateBy = Junction(WorkAuthorCrossRef::class)
    )
    val authors: List<AuthorEntity>
)
