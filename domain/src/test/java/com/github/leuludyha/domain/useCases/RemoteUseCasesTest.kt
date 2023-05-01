package com.github.leuludyha.domain.useCases

import com.github.leuludyha.domain.model.library.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteUseCasesTest {
    lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() { libraryRepository = MockLibraryRepositoryImpl() }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchRemotelyUseCaseGivesExpectedResult() = runTest {
        val res = SearchRemotelyUseCase(libraryRepository)("query").first()
        assertThat(res).isNotNull()
        // TODO How to test PagingData?
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkRemotelyUseCaseGivesCorrectResultOnSuccess() = runTest {
        val data = GetWorkRemotelyUseCase(libraryRepository)(workMrFox.id).first().data
        assertThat(data).isEqualTo(workMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkRemotelyUseCaseGivesErrorOnError() = runTest {
        val result = GetWorkRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionRemotelyUseCaseGivesCorrectResultOnSuccess() = runTest {
        val data = GetEditionRemotelyUseCase(libraryRepository)(editionMrFox.id).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionRemotelyUseCaseGivesErrorOnError() = runTest {
        val result = GetEditionRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorRemotelyUseCaseGivesCorrectResultOnSuccess() = runTest {
        val data = GetAuthorRemotelyUseCase(libraryRepository)(authorRoaldDahl.id).first().data
        assertThat(data).isEqualTo(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorRemotelyUseCaseGivesErrorOnError() = runTest {
        val result = GetAuthorRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionByISBNRemotelyUseCaseGivesCorrectResultOnSuccess() = runTest {
        val data = GetEditionByISBNRemotelyUseCase(libraryRepository)(editionMrFox.isbn13!!).first().data
        assertThat(data).isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionByISBNRemotelyUseCaseGivesErrorOnError() = runTest {
        val result = GetWorkRemotelyUseCase(libraryRepository)("wrongISBN").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }
}