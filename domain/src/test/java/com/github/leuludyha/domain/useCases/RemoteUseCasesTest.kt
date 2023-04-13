package com.github.leuludyha.domain.useCases

import com.github.leuludyha.domain.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoteUseCasesTest {
    lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() = runBlocking {
        libraryRepository = MockLibraryRepositoryImpl()
    }

    @Test
    fun searchRemotelyUseCaseGivesExpectedResult() = runBlocking {
        val res = SearchRemotelyUseCase(libraryRepository)("query").first()
        // TODO How to test PagingData?
    }

    @Test
    fun getWorkRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetWorkRemotelyUseCase(libraryRepository)(workMrFox.id).first().data
        assertThat(data).isEqualTo(workMrFox)
    }

    @Test
    fun getWorkRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetWorkRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetEditionRemotelyUseCase(libraryRepository)(editionMrFox.id).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetEditionRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getAuthorRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetAuthorRemotelyUseCase(libraryRepository)(authorRoaldDahl.id).first().data
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @Test
    fun getAuthorRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetAuthorRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionByISBNRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetEditionByISBNRemotelyUseCase(libraryRepository)(editionMrFox.isbn13!!).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @Test
    fun getEditionByISBNRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetWorkRemotelyUseCase(libraryRepository)("wrongISBN").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }
}