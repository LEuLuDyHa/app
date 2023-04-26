package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [EditionEntity] and many [AuthorEntity]
 */
data class EditionWithAuthors(
    @Embedded val edition: EditionEntity,
    @Relation(
        parentColumn = "editionId",
        entityColumn = "authorId",
        associateBy = Junction(EditionAuthorCrossRef::class)
    )
    val authors: List<AuthorEntity>
)
