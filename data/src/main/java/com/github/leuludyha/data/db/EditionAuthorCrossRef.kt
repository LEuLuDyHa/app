package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition

/**
 * Cross reference between an [EditionEntity] and an [AuthorEntity]
 */
@Entity(primaryKeys = ["editionId", "authorId"])
data class EditionAuthorCrossRef(
    val editionId: String,
    val authorId: String,
)  {
    companion object {
        fun from(edition: Edition, author: Author) = EditionAuthorCrossRef(
            editionId = edition.id,
            authorId = author.id
        )
    }
}
