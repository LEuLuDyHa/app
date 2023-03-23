package com.github.leuludyha.data.db

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.first

class WorksPagingSource(
    val libraryDao: LibraryDao
) : PagingSource<Int, Work>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Work> {
        try {
            val pageIndex = params.key ?: 1
            val works = libraryDao.getAllWorks().first()
                .map { it.toModel(libraryDao) }
            val nextKey = if (works.isEmpty()) null
                else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / 20)
                }
            return LoadResult.Page(
                data = works,
                prevKey = if (pageIndex == 0) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Work>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}