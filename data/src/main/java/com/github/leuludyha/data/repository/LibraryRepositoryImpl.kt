package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.repository.LibraryRepository
import retrofit2.Response
import com.github.leuludyha.domain.util.Result

class LibraryRepositoryImpl(private val libraryRemoteDataSource: LibraryRemoteDataSource) :
    LibraryRepository {

    override suspend fun search(query: String) = libraryRemoteDataSource.search(query)
}