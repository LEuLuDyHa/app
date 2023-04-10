package com.github.leuludyha.data.repository

import com.github.leuludyha.data.repository.datasource.LibraryLocalDataSource
import com.github.leuludyha.data.repository.datasource.LibraryRemoteDataSource
import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.edition1984
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LibraryRepositoryImplTest {
    private lateinit var remoteDataSource: LibraryRemoteDataSource
    private lateinit var localDataSource: LibraryLocalDataSource

    private lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() = runBlocking {
        remoteDataSource = MockLibraryRemoteDataSourceImpl()

        localDataSource = MockLibraryLocalDataSourceImpl()
        localDataSource.saveWork(workMrFox)
        localDataSource.saveEdition(editionMrFox)
        localDataSource.saveAuthor(authorRoaldDahl)

        libraryRepository = LibraryRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun getWorkRemotelyGivesCorrectResultOnSuccess() = runBlocking {
        val data = libraryRepository.getWorkRemotely(workMrFox.id).first().data
        assertThat(data).isEqualTo(workMrFox)
    }

    @Test
    fun getWorkRemotelyGivesErrorOnError() = runBlocking {
        val result = libraryRepository.getWorkRemotely("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionRemotelyGivesCorrectResultOnSuccess() = runBlocking {
        val data = libraryRepository.getEditionRemotely(editionMrFox.id).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionRemotelyGivesErrorOnError() = runBlocking {
        val result = libraryRepository.getEditionRemotely("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getAuthorRemotelyGivesCorrectResultOnSuccess() = runBlocking {
        val data = libraryRepository.getAuthorRemotely(authorRoaldDahl.id).first().data
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @Test
    fun getAuthorRemotelyGivesErrorOnError() = runBlocking {
        val result = libraryRepository.getAuthorRemotely("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionByISBNRemotelyGivesCorrectResultOnSuccess() = runBlocking {
        val data = libraryRepository.getEditionByISBNRemotely(editionMrFox.isbn13!!).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionByISBNRemotelyGivesErrorOnError() = runBlocking {
        val result = libraryRepository.getEditionByISBNRemotely("wrongISBN").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getWorkLocallyReturnsCorrectWork() = runBlocking {
        assertThat(libraryRepository.getWorkLocally(workMrFox.id).first())
            .isEqualTo(workMrFox)
    }

    @Test
    fun getAuthorLocallyReturnsCorrectWork() = runBlocking {
        assertThat(libraryRepository.getAuthorLocally(authorRoaldDahl.id).first())
            .isEqualTo(authorRoaldDahl)
    }

    @Test
    fun getEditionLocallyReturnsCorrectWork() = runBlocking {
        assertThat(libraryRepository.getEditionLocally(editionMrFox.id).first())
            .isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionByISBNLocallyReturnsCorrectWork() = runBlocking {
        assertThat(libraryRepository.getEditionByISBNLocally(editionMrFox.isbn13!!).first())
            .isEqualTo(editionMrFox)
    }

    @Test
    fun saveWorkLocallySavesTheCorrectWork() = runBlocking {
        libraryRepository.saveWorkLocally(work1984)
        assertThat(libraryRepository.getWorkLocally(work1984.id).first())
            .isEqualTo(work1984)
    }

    @Test
    fun saveEditionLocallySavesTheCorrectWork() = runBlocking {
        libraryRepository.saveEditionLocally(edition1984)
        assertThat(libraryRepository.getEditionLocally(edition1984.id).first())
            .isEqualTo(edition1984)
    }

    @Test
    fun saveAuthorLocallySavesTheCorrectWork() = runBlocking {
        libraryRepository.saveAuthorLocally(authorGeorgeOrwell)
        assertThat(libraryRepository.getAuthorLocally(authorGeorgeOrwell.id).first())
            .isEqualTo(authorGeorgeOrwell)
    }
}