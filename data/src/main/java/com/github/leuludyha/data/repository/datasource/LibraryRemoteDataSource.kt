package com.github.leuludyha.data.repository.datasource

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Result
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.Flow

interface LibraryRemoteDataSource {
    fun search(query: String): Flow<PagingData<Work>>
    fun getWork(workId: String): Flow<Result<Work>>
    fun getEdition(editionId: String): Flow<Result<Edition>>
    fun getAuthor(authorId: String): Flow<Result<Author>>
    fun getEditionByISBN(isbn: String): Flow<Result<Edition>>
}