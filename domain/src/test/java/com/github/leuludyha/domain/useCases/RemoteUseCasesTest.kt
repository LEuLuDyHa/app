package com.github.leuludyha.domain.useCases

import com.github.leuludyha.domain.MockDomainLibraryRepository
import com.github.leuludyha.domain.MockDomainLibraryRepository.Companion.mockAuthor
import com.github.leuludyha.domain.MockDomainLibraryRepository.Companion.mockEdition
import com.github.leuludyha.domain.MockDomainLibraryRepository.Companion.mockWork
import com.github.leuludyha.domain.model.library.Result
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.GetAuthorRemotelyUseCase
import com.github.leuludyha.domain.useCase.GetEditionByISBNRemotelyUseCase
import com.github.leuludyha.domain.useCase.GetEditionRemotelyUseCase
import com.github.leuludyha.domain.useCase.GetWorkRemotelyUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RemoteUseCasesTest {
    lateinit var libraryRepository: LibraryRepository

    @Before
    fun setup() = runBlocking {
        libraryRepository = MockDomainLibraryRepository()
    }

    @Test
    fun searchRemotelyUseCaseGivesExpectedResult() = runBlocking {
        //TODO
    }

    @Test
    fun getWorkRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetWorkRemotelyUseCase(libraryRepository)(mockWork.id).first().data
        assertThat(data).isEqualTo(mockWork)
    }

    @Test
    fun getWorkRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetWorkRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetEditionRemotelyUseCase(libraryRepository)(mockEdition.id).first().data
        assertThat(data).isEqualTo(mockEdition)
    }

    @Test
    fun getEditionRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetEditionRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getAuthorRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetAuthorRemotelyUseCase(libraryRepository)(mockAuthor.id).first().data
        assertThat(data).isEqualTo(mockAuthor)
    }

    @Test
    fun getAuthorRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetAuthorRemotelyUseCase(libraryRepository)("wrongId").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }

    @Test
    fun getEditionByISBNRemotelyUseCaseGivesCorrectResultOnSuccess() = runBlocking {
        val data = GetEditionByISBNRemotelyUseCase(libraryRepository)(mockEdition.isbn13!!).first().data
        assertThat(data).isEqualTo(mockEdition)
    }

    @Test
    fun getEditionByISBNRemotelyUseCaseGivesErrorOnError() = runBlocking {
        val result = GetWorkRemotelyUseCase(libraryRepository)("wrongISBN").first()
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat(result.data).isNull()
    }
}