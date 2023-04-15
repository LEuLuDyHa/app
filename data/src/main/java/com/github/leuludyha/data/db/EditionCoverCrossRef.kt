package com.github.leuludyha.data.db

import androidx.room.Entity
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition

@Entity(primaryKeys = ["editionId", "coverId"])
data class EditionCoverCrossRef(
    val editionId: String,
    val coverId: Long,
)  {
    companion object {
        fun from(edition: Edition, cover: Cover) = EditionCoverCrossRef(
            editionId = edition.id,
            coverId = cover.id
        )
    }
}
