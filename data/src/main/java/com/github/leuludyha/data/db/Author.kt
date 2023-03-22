package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Cover
import kotlinx.coroutines.flow.map

import com.github.leuludyha.domain.model.Author as ModelAuthor

@Entity(tableName = "authors")
data class Author (
    @PrimaryKey
    val authorId: String,
    val wikipedia: String?,
    val name: String?,
    val bio: String?,
    val entityType: String?,
): Raw<ModelAuthor> {
    override fun toModel(libraryDao: LibraryDao): ModelAuthor =
        ModelAuthor(
            id = authorId,
            name = name,
            bio = bio,
            photos = libraryDao.getAuthorWithCovers(authorId)
                .map { authorWCover ->
                    authorWCover.covers
                        .map { it.toModel(libraryDao)}
                },
            wikipedia = wikipedia,
            entityType = entityType
        )
}