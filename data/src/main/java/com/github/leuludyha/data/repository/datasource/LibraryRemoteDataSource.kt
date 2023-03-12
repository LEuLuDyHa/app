package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.util.Result
import retrofit2.Response

interface LibraryRemoteDataSource {
    suspend fun search(query: String): Result<Search>
}