package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Work

/**
 * Cross reference between a [WorkEntity] and an [AuthorEntity]
 */
@Entity(primaryKeys = ["workId", "authorId"])
data class WorkAuthorCrossRef(
    val workId: String,
    val authorId: String,
) {
    companion object {
        fun from(work: Work, author: Author) = WorkAuthorCrossRef(
            workId = work.id,
            authorId = author.id
        )
    }
}
