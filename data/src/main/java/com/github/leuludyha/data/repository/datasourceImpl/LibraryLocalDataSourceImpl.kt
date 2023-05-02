package com.github.leuludyha.data.repository.datasourceImpl
import android.content.Context
import android.graphics.Bitmap
import com.github.leuludyha.data.ImageSaver
import com.github.leuludyha.data.db.LibraryDao
import com.github.leuludyha.data.repository.datasource.BitmapProvider
import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.CoverSize
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.model.user.preferences.WorkPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Concrete implementation of the [LibraryLocalDataSource]
 */
class LibraryLocalDataSourceImpl(
    private val context: Context,
    private val bmpProvider: BitmapProvider,
    private val libraryDao: LibraryDao
): LibraryLocalDataSource {
    override fun getWork(workId: String): Flow<Work> =
        libraryDao.getWork(workId).map { it.toModel(libraryDao) }
    override fun getEdition(editionId: String): Flow<Edition> =
        libraryDao.getEdition(editionId).map { it.toModel(libraryDao) }
    override fun getEditionByISBN(isbn: String): Flow<Edition> =
        libraryDao.getEditionByISBN(isbn).map { it.toModel(libraryDao) }
    override fun getAuthor(authorId: String): Flow<Author> =
        libraryDao.getAuthor(authorId).map { it.toModel(libraryDao) }
    override fun getCover(coverId: Long): Flow<Cover> =
        libraryDao.getCover(coverId).map { it.toModel(libraryDao) }
    override fun getWorkPreference(workId: String): Flow<WorkPreference> =
        libraryDao.getWorkPref(workId = workId).map { it.toModel(libraryDao) }
    override fun getAllWorkPreferences(): Flow<List<WorkPreference>> =
        libraryDao.getAllWorkPrefs().map { workPrefs -> workPrefs.map { it.toModel(libraryDao) } }

    override fun getCoverBitmap(cover: Cover, coverSize: CoverSize): Flow<Bitmap> =
        flow { ImageSaver.loadImageToInternalStorage(context, cover.fileNameForSize(coverSize))
                ?.let { emit(it) } }

    override suspend fun save(work: Work) =
        libraryDao.insert(context, bmpProvider, work)
    override suspend fun save(edition: Edition) =
        libraryDao.insert(context, bmpProvider, edition)
    override suspend fun save(author: Author) =
        libraryDao.insert(context, bmpProvider, author)
    override suspend fun save(workPref: WorkPreference) =
        libraryDao.insert(context, bmpProvider, workPref)

    override suspend fun delete(work: Work) =
        libraryDao.delete(context, work)
    override suspend fun delete(edition: Edition) =
        libraryDao.delete(context, edition)
    override suspend fun delete(author: Author) =
        libraryDao.delete(context, author)
    override suspend fun delete(workPref: WorkPreference) =
        libraryDao.delete(workPref)
}