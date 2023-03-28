package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work

interface LibraryRemoteDataSource {
    suspend fun search(query: String): Result<List<Work>>
    suspend fun workById(workId: String): Result<Work>
    suspend fun worksByAuthorId(authorId: String): Result<List<Work>>
    suspend fun editionsByWorkId(workId: String): Result<List<Edition>>
    suspend fun editionById(editionId: String): Result<Edition>
    suspend fun editionByISBN(isbn: Long): Result<Edition>
    suspend fun authorById(authorId: String): Result<Author>
}