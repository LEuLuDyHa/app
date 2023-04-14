package com.github.leuludyha.data.repository.datasourceImpl
import com.github.leuludyha.data.db.LibraryDao
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LibraryLocalDataSourceImpl(private val libraryDao: LibraryDao): LibraryLocalDataSource {
    override fun getWork(workId: String): Flow<Work> =
        libraryDao.getWork(workId).map { it.toModel(libraryDao) }
    override fun getEdition(editionId: String): Flow<Edition> =
        libraryDao.getEdition(editionId).map { it.toModel(libraryDao) }
    override fun getEditionByISBN(isbn: String): Flow<Edition> =
        libraryDao.getEditionByISBN(isbn).map { it.toModel(libraryDao) }
    override fun getAuthor(authorId: String): Flow<Author> =
        libraryDao.getAuthor(authorId).map { it.toModel(libraryDao) }
    override fun getCover(coverId: Long): Flow<Cover> =
        libraryDao.getCover(coverId).map { it.toModel(libraryDao) }

    override suspend fun save(work: Work) =
        libraryDao.insert(work)
    override suspend fun save(edition: Edition) =
        libraryDao.insert(edition)
    override suspend fun save(author: Author) =
        libraryDao.insert(author)

    override suspend fun delete(work: Work) =
        libraryDao.delete(work)
    override suspend fun delete(edition: Edition) =
        libraryDao.delete(edition)
    override suspend fun delete(author: Author) =
        libraryDao.delete(author)
}