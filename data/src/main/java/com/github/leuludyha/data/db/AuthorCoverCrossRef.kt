package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover

/**
 * Cross reference between an [AuthorEntity] and a [CoverEntity]
 */
@Entity(primaryKeys = ["authorId", "coverId"])
data class AuthorCoverCrossRef(
    val authorId: String,
    val coverId: Long,
) {
    companion object {
        fun from(author: Author, cover: Cover) = AuthorCoverCrossRef(
            authorId = author.id,
            coverId = cover.id
        )
    }
}
