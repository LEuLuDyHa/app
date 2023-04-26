package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.library.Author
import kotlinx.coroutines.flow.map

/**
 * Database entity representing an [Author]
 */
@Entity(tableName = "authors")
data class AuthorEntity (
    @PrimaryKey
    val authorId: String,
    val wikipedia: String?,
    val name: String?,
    val birthDate: String?,
    val deathDate: String?,
    //val bio: String?,
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
            covers = libraryDao.getAuthorWithCovers(authorId)
                .map { authorWCover -> authorWCover.covers
                    .map { it.toModel(libraryDao)}
                },
            wikipedia = wikipedia,
        )

    companion object {
        fun from(author: Author) = AuthorEntity(
            authorId = author.id,
            wikipedia = author.wikipedia,
            name = author.name,
            birthDate = author.birthDate,
            deathDate = author.deathDate,
        )
    }
}