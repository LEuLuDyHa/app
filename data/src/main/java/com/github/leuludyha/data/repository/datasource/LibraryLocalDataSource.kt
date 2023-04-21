package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.Flow

interface LibraryLocalDataSource {
    fun getWork(workId: String): Flow<Work>
    fun getEdition(editionId: String): Flow<Edition>
    fun getEditionByISBN(isbn: String): Flow<Edition>
    fun getAuthor(authorId: String): Flow<Author>
    fun getCover(coverId: Long): Flow<Cover>
    suspend fun save(work: Work)
    suspend fun save(edition: Edition)
    suspend fun save(author: Author)
    suspend fun delete(work: Work)
    suspend fun delete(edition: Edition)
    suspend fun delete(author: Author)
}