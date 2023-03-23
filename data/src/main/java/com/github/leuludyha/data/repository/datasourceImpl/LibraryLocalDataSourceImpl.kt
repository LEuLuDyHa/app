package com.github.leuludyha.data.repository.datasourceImpl
import com.github.leuludyha.data.db.*
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Cover
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LibraryLocalDataSourceImpl(private val libraryDao: LibraryDao): LibraryLocalDataSource {
    override fun getWork(workId: String): Flow<Work> =
        libraryDao.getWork(workId).map { it.toModel(libraryDao) }
    override fun getEdition(editionId: String): Flow<Edition> =
        libraryDao.getEdition(editionId).map { it.toModel(libraryDao) }
    override fun getAuthor(authorId: String): Flow<Author> =
        libraryDao.getAuthor(authorId).map { it.toModel(libraryDao) }
    override fun getCover(coverId: Long): Flow<Cover> =
        libraryDao.getCover(coverId).map { it.toModel(libraryDao) }
}