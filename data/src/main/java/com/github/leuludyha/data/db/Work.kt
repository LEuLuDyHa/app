package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.map
import com.github.leuludyha.domain.model.Work as ModelWork

@Entity(tableName = "works")
data class Work (
    @PrimaryKey
    val workId: String,
    val title: String?,
): Raw<ModelWork> {
    override fun toModel(libraryDao: LibraryDao): ModelWork {
        val authorsFlow = libraryDao.getWorkWithAuthors(workId).map { it.authors }
        val modelAuthors = authorsFlow
            .map { authors -> authors
                .map {it.toModel(libraryDao)
                }
            }

        val coversFlow = libraryDao.getWorkWithCovers(workId).map { it.covers }
        val modelCovers = coversFlow
            .map { covers -> covers
                .map {it.toModel(libraryDao)}
            }

        return Work(
            id = workId,
            title = title,
            authors = modelAuthors,
            covers = modelCovers,
            subjects = null //TODO SUBJECTS
        )
    }
}