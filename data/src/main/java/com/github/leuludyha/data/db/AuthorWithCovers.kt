package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * One-to-many relation between one [AuthorEntity] and many [CoverEntity]
 */
data class AuthorWithCovers(
    @Embedded val author: AuthorEntity,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "coverId",
        associateBy = Junction(AuthorCoverCrossRef::class)
    )
    val covers: List<CoverEntity>
)
