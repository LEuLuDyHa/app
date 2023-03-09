package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.Search
import retrofit2.Response

interface SearchRemoteDataSource {
    suspend fun search(query: String): Response<Search>
}