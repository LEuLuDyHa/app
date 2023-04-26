package com.github.leuludyha.data.repository.datasource

import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import kotlinx.coroutines.flow.Flow

/**
 * Provides access to the remote library datasource.
 */
interface LibraryRemoteDataSource {
    /**
     * Search remotely for the most relevant works given this [query].
     * The returned flow emits pages of data of size depending on the configuration we use.
     *
     * @see PagingData
     */
    fun search(query: String): Flow<PagingData<Work>>

    /**
     * Search remotely for the [Work] with the given [workId]. If successful, wraps it in
     * a [Result.Success], otherwise wraps the error in a [Result.Error].
     */
    fun getWork(workId: String): Flow<Result<Work>>

    /**
     * Search remotely for the [Edition] with the given [editionId]. If successful, wraps it in
     * a [Result.Success], otherwise wraps the error in a [Result.Error].
     */
    fun getEdition(editionId: String): Flow<Result<Edition>>

    /**
     * Search remotely for the [Author] with the given [authorId]. If successful, wraps it in
     * a [Result.Success], otherwise wraps the error in a [Result.Error].
     */
    fun getAuthor(authorId: String): Flow<Result<Author>>

    /**
     * Search remotely for the [Edition] with the given [isbn]. If successful, wraps it in
     * a [Result.Success], otherwise wraps the error in a [Result.Error].
     */
    fun getEditionByISBN(isbn: String): Flow<Result<Edition>>
}