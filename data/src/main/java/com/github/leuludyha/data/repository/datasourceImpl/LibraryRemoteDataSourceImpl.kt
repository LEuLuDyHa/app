package com.github.leuludyha.data.repository.datasourceImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.leuludyha.data.api.ApiHelper.rawResponseToModelResult
import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.paging.SearchPagingSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Result
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LibraryRemoteDataSourceImpl(
    private val libraryApi: LibraryApi,
): LibraryRemoteDataSource {

    override fun search(
        query: String,
    ): Flow<PagingData<Work>> {
        return Pager(
            config = PagingConfig(pageSize = 20), // TODO CONSTANT
            pagingSourceFactory = {
                SearchPagingSource(libraryApi = libraryApi, query = query)
            }
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