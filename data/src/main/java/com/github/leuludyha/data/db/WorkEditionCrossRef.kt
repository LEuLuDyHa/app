package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work

/**
 * Cross reference between a [WorkEntity] and an [EditionEntity]
 */
@Entity(primaryKeys = ["workId", "editionId"])
data class WorkEditionCrossRef(
    val workId: String,
    val editionId: String,
) {
    companion object {
        fun from(work: Work, edition: Edition) = WorkEditionCrossRef(
            workId = work.id,
            editionId = edition.id
        )
    }
}
