package com.github.leuludyha.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.leuludyha.data.api.Document
import com.github.leuludyha.data.api.LibraryApi
import com.github.leuludyha.data.db.*
import com.github.leuludyha.domain.model.Work
import kotlinx.coroutines.flow.first

private const val PAGE_SIZE: Int = 20

@OptIn(ExperimentalPagingApi::class)
class LibraryRemoteMediator(
    private val query: String,
    private val libraryApi: LibraryApi,
    private val libraryDatabase: LibraryDatabase
): RemoteMediator<Int, Work>() {

    private val libraryDao = libraryDatabase.libraryDao()
    private val workRemoteKeysDao = libraryDatabase.workRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Work>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevPage = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                prevPage
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextPage = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                nextPage
            }
        }

        try {
            val response = libraryApi.search(query, page, state.config.pageSize)
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()
                endOfPaginationReached = responseData?.documents.isNullOrEmpty()
                responseData?.let {
                    libraryDatabase.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            libraryDao.deleteAllWorks()
                            libraryDao.deleteAllSubjects()
                            libraryDao.deleteAllAuthors()
                            libraryDao.deleteAllCovers()
                            libraryDao.deleteAllEditions()
                            // TODO delete cross refs
                            workRemoteKeysDao.deleteAllWorkRemoteKeys()
                        }

                        var prevPage: Int?
                        var nextPage: Int

                        responseData.start.let { start ->
                            val pageNumber = start / PAGE_SIZE
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 0) null else pageNumber - 1
                        }

                        responseData.documents.forEach{
                            if(!insertDocumentInDatabase(it, prevPage, nextPage))
                                return@withTransaction MediatorResult.Error(Exception())
                        }
                    }
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun insertDocumentInDatabase(document: Document, prevPage: Int?, nextPage: Int): Boolean {
        val modelWork = document.toModel(libraryApi) ?: return false

        // Insert the work
        libraryDao.insert(WorkEntity(
            workId = modelWork.id,
            title = modelWork.title
        ))

        // Insert the remote key
        workRemoteKeysDao.insert(WorkRemoteKeys(
            workId = modelWork.id,
            prevPage = prevPage,
            nextPage = nextPage,
        ))

        // Insert the authors and the CrossRef with the work
        modelWork.authors.first().forEach {
            libraryDao.insert(AuthorEntity(
                authorId = it.id,
                wikipedia = it.wikipedia,
                name = it.name,
                bio = it.bio,
                entityType = it.entityType
            ))
            libraryDao.insert(WorkAuthorCrossRef(modelWork.id, it.id))
        }

        // Insert the covers and the CrossRef with the work
        modelWork.covers.first().forEach {
            libraryDao.insert(CoverEntity(it.id))
            libraryDao.insert(WorkCoverCrossRef(modelWork.id, it.id))
        }

        // Insert the editions and the CrossRef with the work
        modelWork.editions.first().forEach {
            libraryDao.insert(EditionEntity(it.id, it.title))
            libraryDao.insert(WorkEditionCrossRef(modelWork.id, it.id))
        }

        // Insert the subjects and the CrossRef with the work
        modelWork.subjects.first().forEach {
            libraryDao.insert(SubjectEntity(it))
            libraryDao.insert(WorkSubjectCrossRef(modelWork.id, it))
        }

        return true
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Work>,
    ): WorkRemoteKeys? = state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                workRemoteKeysDao.getWorkRemoteKeys(workId = id)
            }
        }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Work>,
    ): WorkRemoteKeys? = state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
        ?.let { work ->
            workRemoteKeysDao.getWorkRemoteKeys(workId = work.id)
        }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Work>,
    ): WorkRemoteKeys? = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
        ?.let { work ->
            workRemoteKeysDao.getWorkRemoteKeys(workId = work.id)
        }
}