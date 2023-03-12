package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.api.SearchApi
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.util.Result
import retrofit2.Response

class LibraryRemoteDataSourceImpl(private val searchApi: SearchApi): LibraryRemoteDataSource {
    override suspend fun search(query: String) = responseToResult(searchApi.search(query))

    private fun responseToResult(response: Response<Search>): Result<Search> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Result.Success(result)
            }
        }
        return Result.Error(response.message())
    }
}