package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [AuthorEntity] and many [WorkEntity]
 */
data class AuthorWithWorks(
    @Embedded val author: AuthorEntity,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "workId",
        associateBy = Junction(WorkAuthorCrossRef::class)
    )
    val works: List<WorkEntity>
)
