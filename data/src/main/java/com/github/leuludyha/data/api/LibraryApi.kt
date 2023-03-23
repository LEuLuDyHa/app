package com.github.leuludyha.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LibraryApi {

    @GET("/search.json")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") resultsPerPage: Int = 20,
    ): Response<RawSearch>

    /**
     * Get a work only by its id. Careful it is not the same as the key.
     * For example the key might be "/works/OL27448W" whereas the work id is only OL27448W.
     */
    @GET("/works/{work_id}.json")
    suspend fun getWork(
        @Path("work_id") workId: String
    ): Response<RawWork>

    @GET("/authors/{author_id}/works.json")
    suspend fun getWorksByAuthorId(
        @Path("author_id") authorId: String
    ): Response<RawAuthorWorks>

    /**
     * Get all editions of a work.
     */
    @GET("/works/{work_id}/editions.json")
    suspend fun getEditionsByWorkId(
        @Path("work_id") workId: String
    ): Response<RawWorkEditions>

    /**
     * Get an edition only by its id. Careful it is not the same as the key.
     * For example the key might be "/books/XXXXXXX" whereas the work id is only XXXXXXX.
     */
    @GET("/books/{edition_id}.json")
    suspend fun getEdition(
        @Path("edition_id") editionId: String
    ): Response<RawEdition>

    @GET("/isbn/{isbn}.json")
    suspend fun getEditionByISBN(
        @Path("isbn") isbn: Long
    ): Response<RawEdition>

    @GET("/authors/{author_id}.json")
    suspend fun getAuthor(
        @Path("author_id") authorId: String
    ): Response<RawAuthor>
}