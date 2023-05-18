package com.github.leuludyha.domain

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockLocalRemoteFailureLibraryRepositoryImpl : LibraryRepository {
    override fun searchRemotely(query: String): Flow<PagingData<Work>> =
        flowOf()

    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        flowOf()

    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> =
        flowOf()

    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> =
        flowOf()

    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        flowOf()

    override suspend fun saveLocally(work: Work) { }

    override suspend fun saveLocally(author: Author)  { }

    override suspend fun saveLocally(edition: Edition)  { }

    override suspend fun saveLocally(workPref: WorkPreference)  { }

    override suspend fun deleteLocally(work: Work)  { }

    override suspend fun deleteLocally(author: Author)  { }

    override suspend fun deleteLocally(edition: Edition)  { }

    override suspend fun deleteLocally(workPref: WorkPreference)  { }

    override fun getWorkLocally(workId: String): Flow<Work> =
        flowOf()

    override fun getAuthorLocally(authorId: String): Flow<Author> =
        flowOf()

    override fun getEditionLocally(editionId: String): Flow<Edition> =
        flowOf()

    override fun getEditionByISBNLocally(isbn: String): Flow<Edition> =
        flowOf()

    override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> =
        flowOf()

    override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> =
        flowOf()

    override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> =
        flowOf(Mocks.bitmap())
}