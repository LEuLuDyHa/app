package com.github.leuludyha.data.api

import com.github.leuludyha.domain.model.CoverSize
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoversApi {
    @GET("/isbn/{isbn}-{size}.jpg")
    suspend fun coverByISBN(
        @Path("isbn") isbn: Long,
        @Path("size") coverSize: CoverSize
    ): Response<ResponseBody>

    @GET("/id/{id}-{size}.jpg")
    suspend fun coverById(
        @Path("id") coverId: Long,
        @Path("size") coverSize: CoverSize
    ): Response<ResponseBody>
}