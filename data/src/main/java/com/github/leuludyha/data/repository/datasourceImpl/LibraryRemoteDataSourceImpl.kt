package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.api.ErrorProne
import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.api.Raw
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.Author
import com.github.leuludyha.domain.model.Edition
import com.github.leuludyha.domain.model.Result
import com.github.leuludyha.domain.model.Work
import retrofit2.Response

class LibraryRemoteDataSourceImpl(private val libraryApi: LibraryApi): LibraryRemoteDataSource {
    override suspend fun search(query: String) =
        rawResponseToModelResult(libraryApi.search(query))
    override suspend fun workById(workId: String): Result<Work> =
        rawResponseToModelResult(libraryApi.workById(workId))
    override suspend fun worksByAuthorId(authorId: String): Result<List<Work>> =
        rawResponseToModelResult(libraryApi.worksByAuthorId(authorId))
    override suspend fun editionsByWorkId(workId: String): Result<List<Edition>> =
        rawResponseToModelResult(libraryApi.editionsByWorkId(workId))
    override suspend fun editionById(editionId: String): Result<Edition> =
        rawResponseToModelResult(libraryApi.editionById(editionId))
    override suspend fun editionByISBN(isbn: Long): Result<Edition> =
        rawResponseToModelResult(libraryApi.editionByISBN(isbn))
    override suspend fun authorById(authorId: String): Result<Author> =
        rawResponseToModelResult(libraryApi.authorById(authorId))

    private fun <TRaw, TModel> rawResponseToModelResult(response: Response<TRaw>): Result<TModel>
        where TRaw : Raw<TModel>, TRaw: ErrorProne
    {
        if(response.isSuccessful){
            // If an error occured => Result.Error
            response.body()?.error?.let { return Result.Error(response.body()!!.error!!) }

            response.body()?.let {result->
                return Result.Success(result.toModel())
            }
        }
        return Result.Error(response.message())
    }
}