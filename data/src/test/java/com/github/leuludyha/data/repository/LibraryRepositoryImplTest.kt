package com.github.leuludyha.data.repository

import com.github.leuludyha.domain.model.library.Mocks.authorGeorgeOrwell
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.edition1984
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.work1984
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFoxPref
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LibraryRepositoryImplTest {
    private lateinit var libraryRepository: LibraryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() = runTest {
        val remoteDataSource = MockLibraryRemoteDataSourceImpl()
        val localDataSource = MockLibraryLocalDataSourceImpl()
        localDataSource.save(workMrFox)
        localDataSource.save(editionMrFox)
        localDataSource.save(authorRoaldDahl)
        localDataSource.save(workMrFoxPref)

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
    fun getWorkPrefLocallyReturnsCorrectWork() = runTest {
        assertThat(libraryRepository.getWorkPrefLocally(workMrFox.id).first())
            .isEqualTo(workMrFoxPref)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveWorkLocallySavesTheCorrectWork() = runTest {
        libraryRepository.saveLocally(work1984)
        assertThat(libraryRepository.getWorkLocally(work1984.id).first())
            .isEqualTo(work1984)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveEditionLocallySavesTheCorrectWork() = runTest {
        libraryRepository.saveLocally(edition1984)
        assertThat(libraryRepository.getEditionLocally(edition1984.id).first())
            .isEqualTo(edition1984)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveAuthorLocallySavesTheCorrectWork() = runTest {
        libraryRepository.saveLocally(authorGeorgeOrwell)
        assertThat(libraryRepository.getAuthorLocally(authorGeorgeOrwell.id).first())
            .isEqualTo(authorGeorgeOrwell)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveWorkPrefLocallySavesTheCorrectWorkPref() = runTest {
        libraryRepository.saveLocally(workMrFoxPref)
        assertThat(libraryRepository.getWorkPrefLocally(workMrFox.id).first())
            .isEqualTo(workMrFoxPref)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingADeletedWorkThrowsException(): Unit {
        assertThrows(Exception::class.java) {
            runTest {
                libraryRepository.saveLocally(workMrFox)
                libraryRepository.deleteLocally(workMrFox)
                libraryRepository.getWorkLocally(workMrFox.id).first()
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingADeletedEditionThrowsException(): Unit {
        assertThrows(Exception::class.java) {
            runTest {
                libraryRepository.saveLocally(editionMrFox)
                libraryRepository.deleteLocally(editionMrFox)
                libraryRepository.getWorkLocally(editionMrFox.id).first()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingADeletedAuthorThrowsException(): Unit {
        assertThrows(Exception::class.java) {
            runTest {
                libraryRepository.saveLocally(authorRoaldDahl)
                libraryRepository.deleteLocally(authorRoaldDahl)
                libraryRepository.getAuthorLocally(authorRoaldDahl.id).first()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gettingADeletedWorkPrefThrowsException(): Unit {
        assertThrows(Exception::class.java) {
            runTest {
                libraryRepository.saveLocally(workMrFoxPref)
                libraryRepository.deleteLocally(workMrFoxPref)
                libraryRepository.getWorkPrefLocally(workMrFox.id).first()
            }
        }
    }
}