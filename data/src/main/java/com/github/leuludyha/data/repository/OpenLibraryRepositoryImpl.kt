package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.SearchRemoteDataSource
import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.repository.OpenLibraryRepository
import retrofit2.Response
import com.github.leuludyha.domain.util.Result

class OpenLibraryRepositoryImpl(private val searchRemoteDataSource: SearchRemoteDataSource) :
    OpenLibraryRepository {

    override suspend fun search(query: String) = responseToResult(searchRemoteDataSource.search(query))

    private fun responseToResult(response: Response<Search>):Result<Search>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Result.Success(result)
            }
        }
        return Result.Error(response.message())
    }
}