package com.github.leuludyha.domain.useCases

import com.github.leuludyha.domain.MockLibraryRepositoryImpl
import com.github.leuludyha.domain.model.library.Author
import com.github.leuludyha.domain.model.library.Cover
import com.github.leuludyha.domain.model.library.Edition
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Work
import com.github.leuludyha.domain.repository.LibraryRepository
import com.github.leuludyha.domain.useCase.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LocalUseCasesTest {
    lateinit var libraryRepository: LibraryRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() = runTest {
        libraryRepository = MockLibraryRepositoryImpl()

        libraryRepository.saveLocally(workMrFox)
        libraryRepository.saveLocally(editionMrFox)
        libraryRepository.saveLocally(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkLocallyUseCaseGivesCorrectResult() = runTest {
        assertThat(GetWorkLocallyUseCase(libraryRepository)(workMrFox.id).first())
            .isEqualTo(workMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionLocallyUseCaseGivesCorrectResult() = runTest {
        assertThat(GetEditionLocallyUseCase(libraryRepository)(editionMrFox.id).first())
            .isEqualTo(editionMrFox)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorLocallyUseCaseGivesCorrectResult() = runTest {
        assertThat(GetAuthorLocallyUseCase(libraryRepository)(authorRoaldDahl.id).first())
            .isEqualTo(authorRoaldDahl)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveWorkLocallyUseCaseCorrectlySavesNewAuthor() = runTest {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveAuthorLocallyUseCaseCorrectlySavesNewAuthor() = runTest {
        val testAuthor = Author(
            id = "TestAuthor",
            name = "Michael Jackson",
            birthDate = "23 janvier 2024",
            deathDate = "10 septembre 2024",
            wikipedia = "wikipedia.com",
            works = flowOf(),
            covers = flowOf()
        )
        SaveAuthorLocallyUseCase(libraryRepository)(testAuthor)
        assertThat(GetAuthorLocallyUseCase(libraryRepository)(testAuthor.id).first())
            .isEqualTo(testAuthor)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveEditionLocallyUseCaseCorrectlySavesNewAuthor() = runTest {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun accessingADeletedWorkThrowsAnException(): Unit = runBlocking {
        val testWork = Work(
            id = "TestWork",
            title = "title",
            editions = flowOf(),
            authors = flowOf(),
            covers = flowOf(),
            subjects = flowOf()
        )
        SaveWorkLocallyUseCase(libraryRepository)(testWork)
        DeleteWorkLocallyUseCase(libraryRepository)(testWork)
        assertThrows(Exception::class.java) { runBlocking {
            GetWorkLocallyUseCase(libraryRepository)(testWork.id).first()
        }}
    }

    @Test
    fun accessingADeletedAuthorThrowsAnException(): Unit = runBlocking {
        val testAuthor = Author(
            id = "TestAuthor",
            name = "Michael Jackson",
            birthDate = "23 janvier 2024",
            deathDate = "10 septembre 2024",
            wikipedia = "wikipedia.com",
            works = flowOf(),
            covers = flowOf()
        )
        SaveAuthorLocallyUseCase(libraryRepository)(testAuthor)
        DeleteAuthorLocallyUseCase(libraryRepository)(testAuthor)
        assertThrows(Exception::class.java) { runBlocking {
            GetAuthorLocallyUseCase(libraryRepository)(testAuthor.id).first()
        }}
    }

    @Test
    fun accessingADeletedEditionThrowsAnException(): Unit = runBlocking {
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
        DeleteEditionLocallyUseCase(libraryRepository)(testEdition)
        assertThrows(Exception::class.java) { runBlocking {
            GetEditionLocallyUseCase(libraryRepository)(testEdition.id).first()
        }}
    }

    @Test
    fun canRecursivelyAccessFieldsOfSavedEdition() = runBlocking {
        val testCover = Cover(1)
        val testAuthor = Author(
            id = "TestAuthor",
            name = "Michael Jackson",
            birthDate = "23 janvier 2024",
            deathDate = "10 septembre 2024",
            wikipedia = "wikipedia.com",
            works = flowOf(),
            covers = flowOf(listOf(testCover))
        )

        val testWork = Work(
            id = "TestWork",
            title = "title",
            editions = flowOf(),
            authors = flowOf(listOf(testAuthor)),
            covers = flowOf(),
            subjects = flowOf()
        )

        val testEdition = Edition(
            id = "testEdition",
            title = "title",
            isbn13 = "isbn13",
            isbn10 = "isbn10",
            works = flowOf(listOf(testWork)),
            authors = flowOf(),
            covers = flowOf(),
        )

        SaveEditionLocallyUseCase(libraryRepository)(testEdition)
        val edition = GetEditionLocallyUseCase(libraryRepository)(testEdition.id).first()
        val works = edition.works.first()
        val authors = works.first().authors.first()
        val covers = authors.first().covers.first()
        assertThat(edition)
            .isEqualTo(testEdition)
        assertThat(works)
            .isEqualTo(listOf(testWork))
        assertThat(authors)
            .isEqualTo(listOf(testAuthor))
        assertThat(covers)
            .isEqualTo(listOf(testCover))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun canRecursivelyAccessFieldsOfSavedWork() = runTest {
        val testCover = Cover(1)
        val testAuthor = Author(
            id = "TestAuthor",
            name = "Michael Jackson",
            birthDate = "23 janvier 2024",
            deathDate = "10 septembre 2024",
            wikipedia = "wikipedia.com",
            works = flowOf(),
            covers = flowOf(listOf(testCover))
        )

        val testWork = Work(
            id = "TestWork",
            title = "title",
            editions = flowOf(),
            authors = flowOf(listOf(testAuthor)),
            covers = flowOf(),
            subjects = flowOf()
        )

        SaveWorkLocallyUseCase(libraryRepository)(testWork)
        val work = GetWorkLocallyUseCase(libraryRepository)(testWork.id).first()
        val authors = work.authors.first()
        val covers = authors.first().covers.first()
        assertThat(work)
            .isEqualTo(testWork)
        assertThat(authors)
            .isEqualTo(listOf(testAuthor))
        assertThat(covers)
            .isEqualTo(listOf(testCover))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun canRecursivelyAccessFieldsOfSavedAuthor() = runTest {
        val testCover = Cover(1)
        val testWork = Work(
            id = "TestWork",
            title = "title",
            editions = flowOf(),
            authors = flowOf(),
            covers = flowOf(listOf(testCover)),
            subjects = flowOf()
        )
        val testAuthor = Author(
            id = "TestAuthor",
            name = "Michael Jackson",
            birthDate = "23 janvier 2024",
            deathDate = "10 septembre 2024",
            wikipedia = "wikipedia.com",
            works = flowOf(listOf(testWork)),
            covers = flowOf()
        )

        SaveAuthorLocallyUseCase(libraryRepository)(testAuthor)
        val author = GetAuthorLocallyUseCase(libraryRepository)(testAuthor.id).first()
        val works = author.works.first()
        val covers = works.first().covers.first()
        assertThat(author)
            .isEqualTo(testAuthor)
        assertThat(works)
            .isEqualTo(listOf(testWork))
        assertThat(covers)
            .isEqualTo(listOf(testCover))
    }
}