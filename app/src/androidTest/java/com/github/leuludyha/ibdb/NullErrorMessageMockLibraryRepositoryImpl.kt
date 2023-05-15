package com.github.leuludyha.ibdb

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import com.github.leuludyha.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NullErrorMessageMockLibraryRepositoryImpl: LibraryRepository {
    override fun searchRemotely(query: String): Flow<PagingData<Work>> {
        TODO("Not yet implemented")
    }

    override fun getWorkRemotely(workId: String): Flow<Result<Work>> =
        flowOf(Result.Error(null))

    override fun getEditionRemotely(editionId: String): Flow<Result<Edition>> {
        TODO("Not yet implemented")
    }

    override fun getEditionByISBNRemotely(isbn: String): Flow<Result<Edition>> {
        TODO("Not yet implemented")
    }

    override fun getAuthorRemotely(authorId: String): Flow<Result<Author>> =
        flowOf(Result.Error(null))


    override suspend fun saveLocally(work: Work) {
        TODO("Not yet implemented")
    }

    override suspend fun saveLocally(author: Author) {
        TODO("Not yet implemented")
    }

    override suspend fun saveLocally(edition: Edition) {
        TODO("Not yet implemented")
    }

    override suspend fun saveLocally(workPref: WorkPreference) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocally(work: Work) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocally(author: Author) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocally(edition: Edition) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocally(workPref: WorkPreference) {
        TODO("Not yet implemented")
    }

    override fun getWorkLocally(workId: String): Flow<Work> {
        TODO("Not yet implemented")
    }

    override fun getAuthorLocally(authorId: String): Flow<Author> {
        TODO("Not yet implemented")
    }

    override fun getEditionLocally(editionId: String): Flow<Edition> {
        TODO("Not yet implemented")
    }

    override fun getEditionByISBNLocally(isbn: String): Flow<Edition> {
        TODO("Not yet implemented")
    }

    override fun getWorkPrefLocally(workId: String): Flow<WorkPreference> {
        TODO("Not yet implemented")
    }

    override fun getAllWorkPrefsLocally(): Flow<List<WorkPreference>> =
        flowOf(listOf())

    override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> {
        TODO("Not yet implemented")
    }

}