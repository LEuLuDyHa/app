package com.github.leuludyha.data.repository.datasourceImpl
import com.github.leuludyha.data.db.*
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import com.github.leuludyha.domain.model.Work as ModelWork

class LibraryLocalDataSourceImpl(private val libraryDao: LibraryDao): LibraryLocalDataSource {
    override fun getWork(workId: String): Flow<ModelWork> =
        libraryDao.getWork(workId).map { it.toModel(libraryDao) }

    override fun getEdition(editionId: String): Flow<Edition> =
        libraryDao.getEdition(editionId)

    override fun getAuthor(authorId: String): Flow<Author> =
        libraryDao.getAuthor(authorId)

    override fun getWorkWithAuthors(workId: String): Flow<WorkWithAuthors> =
        libraryDao.getWorkWithAuthors(workId)

    override fun getWorkWithEditions(workId: String): Flow<WorkWithEditions> =
        libraryDao.getWorkWithEditions(workId)

    override fun getWorkWithCovers(workId: String): Flow<WorkWithCovers> =
        libraryDao.getWorkWithCovers(workId)

    override fun getAuthorWithWorks(authorId: String): Flow<AuthorWithWorks> {
        TODO("Not yet implemented")
    }

    override fun getEditionWithAuthors(editionId: String): Flow<EditionWithAuthors> {
        TODO("Not yet implemented")
    }

    override fun getEditionWithWorks(editionId: String): Flow<EditionWithWorks> {
        TODO("Not yet implemented")
    }

    override fun getEditionWithCovers(editionId: String): Flow<EditionWithCovers> {
        TODO("Not yet implemented")
    }

}