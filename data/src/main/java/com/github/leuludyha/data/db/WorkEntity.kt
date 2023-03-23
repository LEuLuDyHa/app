package com.github.leuludyha.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@Entity(tableName = "works")
data class WorkEntity (
    @PrimaryKey
    val workId: String,
    val title: String?,
): Raw<Work> {
    override fun toModel(libraryDao: LibraryDao): Work {
        val editionsFlow = libraryDao.getWorkWithEditions(workId).map { it.editions }
        val modelEditions = editionsFlow
            .map { editions -> editions
                .map {it.toModel(libraryDao)
                }
            }

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

        val subjectsFlow = libraryDao.getWorkWithSubjects(workId).map { it.subjects }
        val modelSubjects = subjectsFlow
            .map { subjects -> subjects
                .map {it.toModel(libraryDao)}
            }

        return Work(
            id = workId,
            title = title,
            editions = modelEditions,
            authors = modelAuthors,
            covers = modelCovers,
            subjects = modelSubjects
        )
    }
}