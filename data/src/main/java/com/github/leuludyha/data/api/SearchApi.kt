package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search.json")
    suspend fun search(
        @Query("q") query: String
    ): Response<Search>
}