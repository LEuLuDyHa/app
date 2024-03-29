package com.github.leuludyha.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Web Library API. Provides methods to fetch works, editions, authors and to search by a prompt.
 */
interface LibraryApi {

    /**
     * @return the page [page] of size [resultsPerPage] of a search with the given [query]
     */
    @GET("/search.json")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") resultsPerPage: Int = 20,
    ): Response<RawSearch>

    /**
     * @return the [RawWork] corresponding to the given [workId], if any
     */
    @GET("/works/{work_id}.json")
    suspend fun getWork(
        @Path("work_id") workId: String
    ): Response<RawWork>

    /**
     * @return the [RawAuthorWorks] corresponding to the given [authorId], if any
     */
    @GET("/authors/{author_id}/works.json")
    suspend fun getWorksByAuthorId(
        @Path("author_id") authorId: String
    ): Response<RawAuthorWorks>

    /**
     * @return the [RawWorkEditions] corresponding to the given [workId], if any
     */
    @GET("/works/{work_id}/editions.json")
    suspend fun getEditionsByWorkId(
        @Path("work_id") workId: String
    ): Response<RawWorkEditions>

    /**
     * @return the [RawEdition] corresponding to the given [editionId], if any
     */
    @GET("/books/{edition_id}.json")
    suspend fun getEdition(
        @Path("edition_id") editionId: String
    ): Response<RawEdition>

    /**
     * @return the [RawEdition] corresponding to the given [isbn], if any
     */
    @GET("/isbn/{isbn}.json")
    suspend fun getEditionByISBN(
        @Path("isbn") isbn: String
    ): Response<RawEdition>

    /**
     * @return the [RawAuthor] corresponding to the given [authorId], if any
     */
    @GET("/authors/{author_id}.json")
    suspend fun getAuthor(
        @Path("author_id") authorId: String
    ): Response<RawAuthor>
}