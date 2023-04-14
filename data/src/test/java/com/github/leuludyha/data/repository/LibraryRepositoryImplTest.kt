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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LibraryRepositoryImplTest {
    private lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() = runBlocking {
        val remoteDataSource = MockLibraryRemoteDataSourceImpl()

        val localDataSource = MockLibraryLocalDataSourceImpl()
        localDataSource.save(workMrFox)
        localDataSource.save(editionMrFox)
        localDataSource.save(authorRoaldDahl)

        libraryRepository = LibraryRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun searchRemotelyGivesCorrectResultOnSuccess() = runBlocking {
        val data = libraryRepository.searchRemotely("query").first()
        assertThat(data).isNotNull()
        // TODO how to test PagingData content ?
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
        libraryRepository.saveLocally(work1984)
        assertThat(libraryRepository.getWorkLocally(work1984.id).first())
            .isEqualTo(work1984)
    }

    @Test
    fun saveEditionLocallySavesTheCorrectEdition() = runBlocking {
        libraryRepository.saveLocally(edition1984)
        assertThat(libraryRepository.getEditionLocally(edition1984.id).first())
            .isEqualTo(edition1984)
    }

    @Test
    fun saveAuthorLocallySavesTheCorrectAuthor() = runBlocking {
        libraryRepository.saveLocally(authorGeorgeOrwell)
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