package com.github.leuludyha.data.repository.datasourceImpl

import com.github.leuludyha.data.api.SearchApi
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource

class LibraryRemoteDataSourceImpl(private val searchApi: SearchApi): LibraryRemoteDataSource {
    override suspend fun search(query: String) = searchApi.search(query);
}