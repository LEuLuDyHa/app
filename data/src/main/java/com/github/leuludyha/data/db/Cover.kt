package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Cover as ModelCover

@Entity(tableName = "covers")
data class Cover(
    @PrimaryKey
    val coverId: Long
): Raw<ModelCover> {
    override fun toModel(libraryDao: LibraryDao): ModelCover =
        ModelCover(id = coverId)
}
