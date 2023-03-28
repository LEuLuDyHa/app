package com.github.leuludyha.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AuthorWithWorks(
    @Embedded val author: Author,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "workId",
        associateBy = Junction(WorkAuthorCrossRef::class)
    )
    val works: List<Work>
)