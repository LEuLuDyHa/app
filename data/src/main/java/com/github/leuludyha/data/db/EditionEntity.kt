package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.library.Edition
import kotlinx.coroutines.flow.map

/**
 * Database entity representing an [Edition]
 */
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

    companion object {
       fun from(edition: Edition) = EditionEntity(
           editionId = edition.id,
           title = edition.title,
           isbn13 = edition.isbn13,
           isbn10 = edition.isbn10,
       )
    }
}