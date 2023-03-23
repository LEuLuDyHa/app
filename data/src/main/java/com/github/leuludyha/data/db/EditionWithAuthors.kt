package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EditionWithAuthors(
    @Embedded val edition: Edition,
    @Relation(
        parentColumn = "editionId",
        entityColumn = "authorId",
        associateBy = Junction(EditionAuthorCrossRef::class)
    )
    val authors: List<Author>
)
