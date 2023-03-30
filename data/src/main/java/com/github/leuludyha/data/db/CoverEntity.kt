package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.library.Cover

@Entity(tableName = "covers")
data class CoverEntity(
    @PrimaryKey
    val coverId: Long
): Raw<Cover> {
    override fun toModel(libraryDao: LibraryDao): Cover =
        Cover(id = coverId)
}
