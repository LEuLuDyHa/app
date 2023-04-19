package com.github.leuludyha.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.data.db.*
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFoxPref
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibraryDaoTest {

    lateinit var libraryDao: LibraryDao

    @Before
    fun setup() {
        val libraryDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        ).allowMainThreadQueries().build()
        libraryDao = libraryDatabase.libraryDao()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkGivesExpectedResultAfterInsertingIt() = runTest {
        libraryDao.insert(workMrFox)
        assertThat(libraryDao.getWork(workMrFox.id).first()).isEqualTo(WorkEntity.from(workMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionGivesExpectedResultAfterInsertingIt() = runTest {
        libraryDao.insert(editionMrFox)
        assertThat(libraryDao.getEdition(editionMrFox.id).first()).isEqualTo(EditionEntity.from(editionMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorGivesExpectedResultAfterInsertingIt() = runTest {
        libraryDao.insert(authorRoaldDahl)
        assertThat(libraryDao.getAuthor(authorRoaldDahl.id).first()).isEqualTo(AuthorEntity.from(authorRoaldDahl))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCoverGivesExpectedResultAfterInsertingIt() = runTest {
        val cover = authorRoaldDahl.covers.first()[0]
        libraryDao.insert(cover)
        assertThat(libraryDao.getCover(cover.id).first()).isEqualTo(CoverEntity.from(cover))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkPrefWithWorkEntityGivesExpectedResultAfterInsertingIt() = runTest {
        libraryDao.insert(workMrFoxPref)
        val res = libraryDao.getWorkPref(WorkEntity.from(workMrFox)).first()
        assertThat(res).isEqualTo(WorkPrefEntity.from(workMrFoxPref))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkPrefWithWorkIdGivesExpectedResultAfterInsertingIt() = runTest {
        libraryDao.insert(workMrFoxPref)
        val res = libraryDao.getWorkPref(workMrFox.id).first()
        assertThat(res).isEqualTo(WorkPrefEntity.from(workMrFoxPref))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkWithAuthorsGivesExpectedResultAfterInsertingWork() = runTest {
        libraryDao.insert(workMrFox)
        val result = libraryDao.getWorkWithAuthors(workMrFox.id).first()
        val expected = WorkWithAuthors(
            work = WorkEntity.from(workMrFox),
            authors = listOf(AuthorEntity.from(authorRoaldDahl))
        )
        assertThat(result).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkWithEditionsGivesExpectedResultAfterInsertingWork() = runTest {
        libraryDao.insert(workMrFox)
        val result = libraryDao.getWorkWithEditions(workMrFox.id).first()
        val expected = WorkWithEditions(
            work = WorkEntity.from(workMrFox),
            editions = listOf(EditionEntity.from(editionMrFox))
        )
        assertThat(result).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkWithCoversGivesExpectedResultAfterInsertingWork() = runTest {
        libraryDao.insert(workMrFox)
        val result = libraryDao.getWorkWithCovers(workMrFox.id).first()
        val covers = workMrFox.covers.first()
        val expected = WorkWithCovers(
            work = WorkEntity.from(workMrFox),
            covers = covers.map { CoverEntity.from(it) }
        )
        assertThat(result.work).isEqualTo(expected.work)
        assertThat(result.covers.toSet()).isEqualTo(expected.covers.toSet()) // order is not preserved
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getWorkWithSubjectsGivesExpectedResultAfterInsertingWork() = runTest {
        libraryDao.insert(workMrFox)
        val result = libraryDao.getWorkWithSubjects(workMrFox.id).first()
        val subjects = workMrFox.subjects.first()
        val expected = WorkWithSubjects(
            work = WorkEntity.from(workMrFox),
            subjects = subjects.map { SubjectEntity.from(it) }
        )
        assertThat(result.work).isEqualTo(expected.work)
        assertThat(result.subjects.toSet()).isEqualTo(expected.subjects.toSet()) // order is not preserved
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorWithWorksGivesExpectedResultAfterInsertingAuthor() = runTest {
        libraryDao.insert(authorRoaldDahl)
        val result = libraryDao.getAuthorWithWorks(authorRoaldDahl.id).first()
        val expected = AuthorWithWorks(
            author = AuthorEntity.from(authorRoaldDahl),
            works = listOf(WorkEntity.from(workMrFox))
        )
        assertThat(result).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAuthorWithCoversGivesExpectedResultAfterInsertingAuthor() = runTest {
        libraryDao.insert(authorRoaldDahl)
        val result = libraryDao.getAuthorWithCovers(authorRoaldDahl.id).first()
        val covers = authorRoaldDahl.covers.first()
        val expected = AuthorWithCovers(
            author = AuthorEntity.from(authorRoaldDahl),
            covers = covers.map { CoverEntity.from(it) }
        )
        assertThat(result.author).isEqualTo(expected.author)
        assertThat(result.covers.toSet()).isEqualTo(expected.covers.toSet()) // order is not preserved
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionWithWorksGivesExpectedResultAfterInsertingEdition() = runTest {
        libraryDao.insert(editionMrFox)
        val result = libraryDao.getEditionWithWorks(editionMrFox.id).first()
        val expected = EditionWithWorks(
            edition = EditionEntity.from(editionMrFox),
            works = listOf(WorkEntity.from(workMrFox))
        )
        assertThat(result).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionWithAuthorsGivesExpectedResultAfterInsertingEdition() = runTest {
        libraryDao.insert(editionMrFox)
        val result = libraryDao.getEditionWithAuthors(editionMrFox.id).first()
        val expected = EditionWithAuthors(
            edition = EditionEntity.from(editionMrFox),
            authors = listOf(AuthorEntity.from(authorRoaldDahl))
        )
        assertThat(result).isEqualTo(expected)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEditionWithCoversGivesExpectedResultAfterInsertingEdition() = runTest {
        libraryDao.insert(editionMrFox)
        val result = libraryDao.getEditionWithCovers(editionMrFox.id).first()
        val covers = editionMrFox.covers.first()
        val expected = EditionWithCovers(
            edition = EditionEntity.from(editionMrFox),
            covers = covers.map { CoverEntity.from(it) }
        )
        assertThat(result.edition).isEqualTo(expected.edition)
        assertThat(result.covers.toSet()).isEqualTo(expected.covers.toSet())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessAWorkAfterDeletingItReturnsNull() = runTest {
        libraryDao.insert(workMrFox)
        libraryDao.delete(workMrFox)
        val result = libraryDao.getWork(workMrFox.id).first()
        assertThat(result).isNull()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessAnAuthorAfterDeletingItReturnsNull() = runTest {
        libraryDao.insert(authorRoaldDahl)
        libraryDao.delete(authorRoaldDahl)
        val result = libraryDao.getAuthor(authorRoaldDahl.id).first()
        assertThat(result).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessCoversAfterDeletingItReturnsEmpty() = runTest {
        val cover = editionMrFox.covers.first()[0]
        libraryDao.insert(cover)
        libraryDao.delete(cover)
        val result = libraryDao.getCover(cover.id).first()
        assertThat(result).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessAnEditionAfterDeletingItReturnsNull() = runTest {
        libraryDao.insert(editionMrFox)
        libraryDao.delete(editionMrFox)
        val result = libraryDao.getEdition(editionMrFox.id).first()
        assertThat(result).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessSubjectsAfterDeletingItReturnsEmpty() = runTest {
        libraryDao.insert(workMrFox)
        val subjects = workMrFox.subjects.first()
        subjects.forEach { libraryDao.deleteSubject(it) }
        val result = libraryDao.getWorkWithSubjects(workMrFox.id).first()
        assertThat(result.subjects).isEmpty()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessWorkPrefAfterDeletingItReturnsNull() = runTest {
        libraryDao.insert(workMrFoxPref)
        libraryDao.delete(workMrFoxPref)
        val result = libraryDao.getWorkPref(workMrFox.id).first()
        assertThat(result).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun tryingToAccessWorkPrefAfterDeletingItUsingEntityReturnsNull() = runTest {
        libraryDao.insert(workMrFoxPref)
        libraryDao.deleteWorkPref(WorkEntity.from(workMrFox))
        val result = libraryDao.getWorkPref(workMrFox.id).first()
        assertThat(result).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingWorkWithoutAnyAuthorsWorksAsExpected() = runTest {
        libraryDao.insert(workMrFox.copy(authors = flowOf()))
        val result = libraryDao.getWork(workMrFox.id).first()
        assertThat(result).isEqualTo(WorkEntity.from(workMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingWorkWithoutAnyEditionsWorksAsExpected() = runTest {
        libraryDao.insert(workMrFox.copy(editions = flowOf()))
        val result = libraryDao.getWork(workMrFox.id).first()
        assertThat(result).isEqualTo(WorkEntity.from(workMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingWorkWithoutAnySubjectsWorksAsExpected() = runTest {
        libraryDao.insert(workMrFox.copy(subjects = flowOf()))
        val result = libraryDao.getWork(workMrFox.id).first()
        assertThat(result).isEqualTo(WorkEntity.from(workMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingWorkWithoutAnyCoversWorksAsExpected() = runTest {
        libraryDao.insert(workMrFox.copy(covers = flowOf()))
        val result = libraryDao.getWork(workMrFox.id).first()
        assertThat(result).isEqualTo(WorkEntity.from(workMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingEditionWithoutAnyAuthorsWorksAsExpected() = runTest {
        libraryDao.insert(editionMrFox.copy(authors = flowOf()))
        val result = libraryDao.getEdition(editionMrFox.id).first()
        assertThat(result).isEqualTo(EditionEntity.from(editionMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingEditionWithoutAnyWorksWorksAsExpected() = runTest {
        libraryDao.insert(editionMrFox.copy(works = flowOf()))
        val result = libraryDao.getEdition(editionMrFox.id).first()
        assertThat(result).isEqualTo(EditionEntity.from(editionMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingEditionWithoutAnyCoversWorksAsExpected() = runTest {
        libraryDao.insert(editionMrFox.copy(covers = flowOf()))
        val result = libraryDao.getEdition(editionMrFox.id).first()
        assertThat(result).isEqualTo(EditionEntity.from(editionMrFox))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingAuthorWithoutAnyWorksWorksAsExpected() = runTest {
        libraryDao.insert(authorRoaldDahl.copy(works = flowOf()))
        val result = libraryDao.getAuthor(authorRoaldDahl.id).first()
        assertThat(result).isEqualTo(AuthorEntity.from(authorRoaldDahl))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertingAuthorWithoutAnyCoversWorksAsExpected() = runTest {
        libraryDao.insert(authorRoaldDahl.copy(covers = flowOf()))
        val result = libraryDao.getAuthor(authorRoaldDahl.id).first()
        assertThat(result).isEqualTo(AuthorEntity.from(authorRoaldDahl))
    }
}