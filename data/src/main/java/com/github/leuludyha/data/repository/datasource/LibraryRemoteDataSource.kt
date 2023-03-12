package com.github.leuludyha.data.repository.datasource

import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.model.Result

interface LibraryRemoteDataSource {
    suspend fun search(query: String): Result<Search>
}