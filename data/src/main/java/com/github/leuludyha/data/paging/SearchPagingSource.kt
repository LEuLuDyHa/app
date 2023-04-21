package com.github.leuludyha.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.domain.model.library.Work
import retrofit2.HttpException

/**
 * [PagingSource] using a [LibraryApi] as a data source
 */
class SearchPagingSource(
    private val libraryApi: LibraryApi,
    private val query: String
) : PagingSource<Int, Work>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Work> {
        val currentPage = params.key ?: 1
        return try {
            val response = libraryApi.search(query = query, page = currentPage, resultsPerPage = 20) // TODO CONSTANT
            val body = response.body()
            if(!response.isSuccessful || body == null)
                return LoadResult.Error(HttpException(response))

            val endOfPaginationReached = body.documents.isEmpty()
            if (!endOfPaginationReached) {
                LoadResult.Page(
                    data = response.body()!!.documents.mapNotNull {it.toModel(libraryApi) },
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Work>): Int? {
        return state.anchorPosition
    }
}