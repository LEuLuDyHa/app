package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Work

/**
 * Cross reference between a [WorkEntity] and a [CoverEntity]
 */
@Entity(primaryKeys = ["workId", "coverId"])
data class WorkCoverCrossRef(
    val workId: String,
    val coverId: Long,
) {
    companion object {
        fun from(work: Work, cover: Cover) = WorkCoverCrossRef(
            workId = work.id,
            coverId = cover.id
        )
    }
}
