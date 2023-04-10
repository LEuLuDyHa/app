package com.github.leuludyha.data.repository

import com.github.leuludyha.data.RequiringLibraryRepositoryTest
import com.github.leuludyha.data.TestUtils.author1
import com.github.leuludyha.data.TestUtils.edition1
import com.github.leuludyha.data.TestUtils.work1
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.useCase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

/*
 * I couldn't write those tests at the domain layer because I need to instantiate an API and
 * a database etc, which are not available at that layer.
 *
 * Moreover I didn't write any RepositoryTest because every method of the repository should be
 * covered by UseCases.
 */

class LocalUseCasesTest: RequiringLibraryRepositoryTest() {

    @Test
    fun getWorkLocallyUseCaseGivesCorrectResult() = runBlocking {
        assertThat(GetWorkLocallyUseCase(libraryRepository)(work1.workId).first())
            .isEqualTo(work1.toModel(libraryDao))
    }

    @Test
    fun getEditionLocallyUseCaseGivesCorrectResult() = runBlocking {
        assertThat(GetEditionLocallyUseCase(libraryRepository)(edition1.editionId).first())
            .isEqualTo(edition1.toModel(libraryDao))
    }

    @Test
    fun getAuthorLocallyUseCaseGivesCorrectResult() = runBlocking {
        assertThat(GetAuthorLocallyUseCase(libraryRepository)(author1.authorId).first())
            .isEqualTo(author1.toModel(libraryDao))
    }

    @Test
    fun saveWorkLocallyUseCaseCorrectlySavesNewAuthor() = runBlocking {
        val testWork = Work(
            id = "TestWork",
            title = "title",
            editions = flowOf(),
            authors = flowOf(),
            covers = flowOf(),
            subjects = flowOf()
        )
        SaveWorkLocallyUseCase(libraryRepository)(testWork)
        assertThat(GetWorkLocallyUseCase(libraryRepository)(testWork.id).first())
            .isEqualTo(testWork)
    }

    @Test
    fun saveAuthorLocallyUseCaseCorrectlySavesNewAuthor() = runBlocking {
        val testAuthor = Author(
            id = "TestAuthor",
            name = "Michael Jackson",
            birthDate = "23 janvier 2024",
            deathDate = "10 septembre 2024",
            wikipedia = "wikipedia.com",
            works = flowOf(),
            photos = flowOf()
        )
        SaveAuthorLocallyUseCase(libraryRepository)(testAuthor)
        assertThat(GetAuthorLocallyUseCase(libraryRepository)(testAuthor.id).first())
            .isEqualTo(testAuthor)
    }

    @Test
    fun saveEditionLocallyUseCaseCorrectlySavesNewAuthor() = runBlocking {
        val testEdition = Edition(
            id = "testEdition",
            title = "title",
            isbn13 = "isbn13",
            isbn10 = "isbn10",
            works = flowOf(),
            authors = flowOf(),
            covers = flowOf(),
        )
        SaveEditionLocallyUseCase(libraryRepository)(testEdition)
        assertThat(GetEditionLocallyUseCase(libraryRepository)(testEdition.id).first())
            .isEqualTo(testEdition)
    }
}