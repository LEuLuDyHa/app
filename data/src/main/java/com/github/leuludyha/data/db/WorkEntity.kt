package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.map

@Entity(tableName = "works")
data class WorkEntity (
    @PrimaryKey
    val workId: String,
    val title: String?,
): Raw<Work> {
    override fun toModel(libraryDao: LibraryDao): Work {
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