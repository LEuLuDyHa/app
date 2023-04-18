package com.github.leuludyha.data.repository

import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.edition1984
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LibraryRepositoryImplTest {
    private lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() = runTest {
        remoteDataSource = MockLibraryRemoteDataSourceImpl()

        val localDataSource = MockLibraryLocalDataSourceImpl()
        localDataSource.save(workMrFox)
        localDataSource.save(editionMrFox)
        localDataSource.save(authorRoaldDahl)

        libraryRepository = LibraryRepositoryImpl(remoteDataSource, localDataSource)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchRemotelyGivesCorrectResultOnSuccess() = runTest {
        val data = libraryRepository.searchRemotely("query").first()
        assertThat(data).isNotNull()
        // TODO how to test PagingData content ?
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkRemotelyGivesCorrectResultOnSuccess() = runTest {
        val data = libraryRepository.getWorkRemotely(workMrFox.id).first().data
        assertThat(data).isEqualTo(workMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkRemotelyGivesErrorOnError() = runTest {
        val result = libraryRepository.getWorkRemotely("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionRemotelyGivesCorrectResultOnSuccess() = runTest {
        val data = libraryRepository.getEditionRemotely(editionMrFox.id).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionRemotelyGivesErrorOnError() = runTest {
        val result = libraryRepository.getEditionRemotely("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorRemotelyGivesCorrectResultOnSuccess() = runTest {
        val data = libraryRepository.getAuthorRemotely(authorRoaldDahl.id).first().data
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorRemotelyGivesErrorOnError() = runTest {
        val result = libraryRepository.getAuthorRemotely("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionByISBNRemotelyGivesCorrectResultOnSuccess() = runTest {
        val data = libraryRepository.getEditionByISBNRemotely(editionMrFox.isbn13!!).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionByISBNRemotelyGivesErrorOnError() = runTest {
        val result = libraryRepository.getEditionByISBNRemotely("wrongISBN").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkLocallyReturnsCorrectWork() = runTest {
        assertThat(libraryRepository.getWorkLocally(workMrFox.id).first())
            .isEqualTo(workMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorLocallyReturnsCorrectWork() = runTest {
        assertThat(libraryRepository.getAuthorLocally(authorRoaldDahl.id).first())
            .isEqualTo(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionLocallyReturnsCorrectWork() = runTest {
        assertThat(libraryRepository.getEditionLocally(editionMrFox.id).first())
            .isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionByISBNLocallyReturnsCorrectWork() = runTest {
        assertThat(libraryRepository.getEditionByISBNLocally(editionMrFox.isbn13!!).first())
            .isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveWorkLocallySavesTheCorrectWork() = runTest {
        libraryRepository.saveWorkLocally(work1984)
        assertThat(libraryRepository.getWorkLocally(work1984.id).first())
            .isEqualTo(work1984)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveEditionLocallySavesTheCorrectWork() = runTest {
        libraryRepository.saveEditionLocally(edition1984)
        assertThat(libraryRepository.getEditionLocally(edition1984.id).first())
            .isEqualTo(edition1984)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveAuthorLocallySavesTheCorrectWork() = runTest {
        libraryRepository.saveAuthorLocally(authorGeorgeOrwell)
        assertThat(libraryRepository.getAuthorLocally(authorGeorgeOrwell.id).first())
            .isEqualTo(authorGeorgeOrwell)
    }

    @Test
    fun gettingADeletedWorkThrowsException(): Unit = runBlocking {
        libraryRepository.saveLocally(workMrFox)
        libraryRepository.deleteLocally(workMrFox)
        Assert.assertThrows(Exception::class.java) {
            runBlocking {
                libraryRepository.getWorkLocally(workMrFox.id).first()
            }
        }
    }

    @Test
    fun gettingADeletedEditionThrowsException(): Unit = runBlocking {
        libraryRepository.saveLocally(editionMrFox)
        libraryRepository.deleteLocally(editionMrFox)
        Assert.assertThrows(Exception::class.java) {
            runBlocking {
                libraryRepository.getWorkLocally(editionMrFox.id).first()
            }
        }
    }

    @Test
    fun gettingADeletedAuthorThrowsException(): Unit = runBlocking {
        libraryRepository.saveLocally(authorRoaldDahl)
        libraryRepository.deleteLocally(authorRoaldDahl)
        Assert.assertThrows(Exception::class.java) {
            runBlocking {
                libraryRepository.getAuthorLocally(authorRoaldDahl.id).first()
            }
        }
    }
}