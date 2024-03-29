package com.github.leuludyha.data.repository

import androidx.paging.PagingData
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.library.*
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockLibraryRemoteDataSourceImpl: LibraryRemoteDataSource {
    override fun search(query: String): Flow<PagingData<Work>> =
        flowOf(PagingData.from(listOf(workMrFox)))

    override fun getWork(workId: String): Flow<Result<Work>> =
        if (workId == workMrFox.id)
            flowOf(Result.Success(workMrFox))
        else
            flowOf(Result.Error("id not found"))

    override fun getEdition(editionId: String): Flow<Result<Edition>> =
        if (editionId == Mocks.editionMrFox.id)
            flowOf(Result.Success(Mocks.editionMrFox))
        else
            flowOf(Result.Error("id not found"))

    override fun getEditionByISBN(isbn: String): Flow<Result<Edition>> =
        if (isbn == Mocks.editionMrFox.isbn10 || isbn == Mocks.editionMrFox.isbn13)
            flowOf(Result.Success(Mocks.editionMrFox))
        else
            flowOf(Result.Error("isbn not found"))

    override fun getAuthor(authorId: String): Flow<Result<Author>> =
        if (authorId == Mocks.authorRoaldDahl.id)
            flowOf(Result.Success(Mocks.authorRoaldDahl))
        else
            flowOf(Result.Error("id not found"))

}