package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Author
import kotlinx.coroutines.flow.map


@Entity(tableName = "authors")
data class AuthorEntity (
    @PrimaryKey
    val authorId: String,
    val wikipedia: String?,
    val name: String?,
    val birthDate: String?,
    val deathDate: String?,
    //val bio: String?,
    val entityType: String?,
): Raw<Author> {
    override fun toModel(libraryDao: LibraryDao): Author =
        Author(
            id = authorId,
            name = name,
            birthDate = birthDate,
            deathDate = deathDate,
            //bio = bio,
            works = libraryDao.getAuthorWithWorks(authorId)
                .map { authorWWorks -> authorWWorks.works
                    .map { it.toModel(libraryDao) }
                 },
            photos = libraryDao.getAuthorWithCovers(authorId)
                .map { authorWCover -> authorWCover.covers
                    .map { it.toModel(libraryDao)}
                },
            wikipedia = wikipedia,
        )
}