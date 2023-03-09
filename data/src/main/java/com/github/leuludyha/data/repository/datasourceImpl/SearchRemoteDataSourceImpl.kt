package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.api.SearchApi
import com.github.leuludyha.data.repository.datasource.SearchRemoteDataSource

class SearchRemoteDataSourceImpl(private val searchApi: SearchApi): SearchRemoteDataSource {
    override suspend fun search(query: String) = searchApi.search(query);
}