package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Edition
import kotlinx.coroutines.flow.map

@Entity(tableName = "editions")
data class EditionEntity (
    @PrimaryKey
    val editionId: String,
    val title: String?,
    val isbn13: String?,
    val isbn10: String?,
): Raw<Edition> {
    override fun toModel(libraryDao: LibraryDao): Edition =
        Edition(
            id = editionId,
            title = title,
            isbn13 = isbn13,
            isbn10 = isbn10,
            authors = libraryDao.getEditionWithAuthors(editionId)
                .map { editionWAuthors -> editionWAuthors.authors
                    .map { it.toModel(libraryDao) }
                },
            works = libraryDao.getEditionWithWorks(editionId)
                .map { editionWWorks -> editionWWorks.works
                    .map { it.toModel(libraryDao) }
                },
            covers = libraryDao.getEditionWithCovers(editionId)
                .map { editionWCovers -> editionWCovers.covers
                    .map { it.toModel(libraryDao) }
                },
        )
}