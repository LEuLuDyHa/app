package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.data.db.*
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.Flow


interface LibraryLocalDataSource {
    fun getWork(workId: String): Flow<Work>

    fun getEdition(editionId: String): Flow<EditionEntity>

    fun getAuthor(authorId: String): Flow<AuthorEntity>

    fun getWorkWithAuthors(workId: String): Flow<WorkWithAuthors>

    fun getWorkWithEditions(workId: String): Flow<WorkWithEditions>

    fun getWorkWithCovers(workId: String): Flow<WorkWithCovers>

    fun getAuthorWithWorks(authorId: String): Flow<AuthorWithWorks>

    fun getEditionWithAuthors(editionId: String): Flow<EditionWithAuthors>

    fun getEditionWithWorks(editionId: String): Flow<EditionWithWorks>

    fun getEditionWithCovers(editionId: String): Flow<EditionWithCovers>
}