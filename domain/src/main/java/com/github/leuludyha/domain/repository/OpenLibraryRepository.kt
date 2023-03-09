package com.github.leuludyha.domain.repository

import com.github.leuludyha.domain.model.Search
import com.github.leuludyha.domain.util.Result

interface OpenLibraryRepository {
    suspend fun search(query: String): Result<Search>
}