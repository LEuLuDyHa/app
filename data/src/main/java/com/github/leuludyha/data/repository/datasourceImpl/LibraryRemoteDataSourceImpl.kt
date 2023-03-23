package com.github.leuludyha.data.repository.datasourceImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.leuludyha.data.api.*
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModelResult
import com.github.leuludyha.data.db.LibraryDatabase
import com.github.leuludyha.data.db.WorksPagingSource
import com.github.leuludyha.data.paging.LibraryRemoteMediator
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class LibraryRemoteDataSourceImpl(
    private val libraryApi: LibraryApi,
    private val libraryDatabase: LibraryDatabase
): LibraryRemoteDataSource {
    private val libraryDao = libraryDatabase.libraryDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun search(
        query: String,
        page: Int,
        resultsPerPage: Int,
    ): Flow<PagingData<Work>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = LibraryRemoteMediator(
                query,
                libraryApi,
                libraryDatabase
            ),
            pagingSourceFactory = {  WorksPagingSource(libraryDao) },
        ).flow
    }

    override fun getWork(workId: String): Flow<Result<Work>> = flow {
        emit(rawResponseToModelResult(libraryApi.getWork(workId), libraryApi))
    }

    override fun getEdition(editionId: String): Flow<Result<Edition>> = flow {
        emit(rawResponseToModelResult(libraryApi.getEdition(editionId), libraryApi))
    }

    override fun getAuthor(authorId: String): Flow<Result<Author>> = flow {
        emit(rawResponseToModelResult(libraryApi.getAuthor(authorId), libraryApi))
    }

    override fun getWorksByAuthor(authorId: String): Flow<Result<List<Work>>> = flow {
        emit(rawResponseToModelResult(libraryApi.getWorksByAuthorId(authorId), libraryApi))
    }

    override fun getEditionsByWork(workId: String): Flow<Result<List<Edition>>> = flow {
        emit(rawResponseToModelResult(libraryApi.getEditionsByWorkId(workId), libraryApi))
    }

    override fun getEditionByISBN(isbn: Long): Flow<Result<Edition>> = flow {
        emit(rawResponseToModelResult(libraryApi.getEditionByISBN(isbn), libraryApi))
    }
}