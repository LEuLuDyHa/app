package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.data.db.*
import kotlinx.coroutines.flow.Flow

import com.github.leuludyha.domain.model.Work as ModelWork

interface LibraryLocalDataSource {
    fun getWork(workId: String): Flow<ModelWork>

    fun getEdition(editionId: String): Flow<Edition>

    fun getAuthor(authorId: String): Flow<Author>

    fun getWorkWithAuthors(workId: String): Flow<WorkWithAuthors>

    fun getWorkWithEditions(workId: String): Flow<WorkWithEditions>

    fun getWorkWithCovers(workId: String): Flow<WorkWithCovers>

    fun getAuthorWithWorks(authorId: String): Flow<AuthorWithWorks>

    fun getEditionWithAuthors(editionId: String): Flow<EditionWithAuthors>

    fun getEditionWithWorks(editionId: String): Flow<EditionWithWorks>

    fun getEditionWithCovers(editionId: String): Flow<EditionWithCovers>
}