package com.github.leuludyha.data.repository

import android.graphics.Bitmap
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Mocks
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockLibraryLocalDataSourceImpl: LibraryLocalDataSource {
    private val savedWorks: HashMap<String, Work> = hashMapOf()
    private val savedEditions: HashMap<String, Edition> = hashMapOf()
    private val savedAuthors: HashMap<String, Author> = hashMapOf()
    private val savedWorkPrefs: HashMap<String, WorkPreference> = hashMapOf()

    override fun getWork(workId: String): Flow<Work> =
        if (savedWorks[workId] != null)
            flowOf(savedWorks[workId]!!)
        else
            flowOf()

    override fun getEdition(editionId: String): Flow<Edition> =
        if (savedEditions[editionId] != null)
            flowOf(savedEditions[editionId]!!)
        else
            flowOf()

    override fun getEditionByISBN(isbn: String): Flow<Edition> {
        val editions = savedEditions.values.filter { it.isbn10 == isbn || it.isbn13 == isbn }
        return if(editions.firstOrNull() == null)
            flowOf()
        else
            flowOf(editions.first())
    }

    override fun getAuthor(authorId: String): Flow<Author> =
        if (savedAuthors[authorId] != null)
            flowOf(savedAuthors[authorId]!!)
        else
            flowOf()

    override fun getCover(coverId: Long): Flow<Cover> = flowOf(Cover(coverId))
    override fun getWorkPreference(workId: String): Flow<WorkPreference> =
        if (savedWorkPrefs[workId] != null)
            flowOf(savedWorkPrefs[workId]!!)
        else
            flowOf()

    override fun getAllWorkPreferences(): Flow<List<WorkPreference>> =
        flowOf(savedWorkPrefs.values.toList())

    override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> =
        flowOf(Mocks.bitmap())

    override suspend fun save(work: Work) {
        savedWorks[work.id] = work
    }

    override suspend fun save(author: Author) {
        savedAuthors[author.id] = author
    }

    override suspend fun save(workPref: WorkPreference) {
        savedWorkPrefs[workPref.work.id] = workPref
    }

    override suspend fun delete(work: Work) {
        savedWorks.remove(work.id)
    }

    override suspend fun delete(edition: Edition) {
        savedEditions.remove(edition.id)
    }

    override suspend fun delete(author: Author) {
        savedAuthors.remove(author.id)
    }

    override suspend fun delete(workPref: WorkPreference) {
        savedWorkPrefs.remove(workPref.work.id)
    }

    override suspend fun save(edition: Edition) {
        savedEditions[edition.id] = edition
    }
}