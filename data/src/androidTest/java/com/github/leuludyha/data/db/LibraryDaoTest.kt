package com.github.leuludyha.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.data.db.*
import com.github.leuludyha.domain.model.library.Mocks.authorRoaldDahl
import com.github.leuludyha.domain.model.library.Mocks.editionMrFox
import com.github.leuludyha.domain.model.library.Mocks.workMrFox
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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

    @Test
    fun getWorkGivesExpectedResultAfterInsertingIt() = runBlocking {
        libraryDao.insert(workMrFox)
        assertThat(libraryDao.getWork(workMrFox.id).first()).isEqualTo(WorkEntity.from(workMrFox))
    }

    @Test
    fun getEditionGivesExpectedResultAfterInsertingIt() = runBlocking {
        libraryDao.insert(editionMrFox)
        assertThat(libraryDao.getEdition(editionMrFox.id).first()).isEqualTo(EditionEntity.from(editionMrFox))
    }

    @Test
    fun getAuthorGivesExpectedResultAfterInsertingIt() = runBlocking {
        libraryDao.insert(authorRoaldDahl)
        assertThat(libraryDao.getAuthor(authorRoaldDahl.id).first()).isEqualTo(AuthorEntity.from(authorRoaldDahl))
    }

    @Test
    fun getCoverGivesExpectedResultAfterInsertingIt() = runBlocking {
        val cover = authorRoaldDahl.covers.first()[0]
        libraryDao.insert(cover)
        assertThat(libraryDao.getCover(cover.id).first()).isEqualTo(CoverEntity.from(cover))
    }

    @Test
    fun getWorkWithAuthorsGivesExpectedResultAfterInsertingWork() = runBlocking {
        libraryDao.insert(workMrFox)
        val result = libraryDao.getWorkWithAuthors(workMrFox.id).first()
        val expected = WorkWithAuthors(
            work = WorkEntity.from(workMrFox),
            authors = listOf(AuthorEntity.from(authorRoaldDahl))
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getWorkWithEditionsGivesExpectedResultAfterInsertingWork() = runBlocking {
        libraryDao.insert(workMrFox)
        val result = libraryDao.getWorkWithEditions(workMrFox.id).first()
        val expected = WorkWithEditions(
            work = WorkEntity.from(workMrFox),
            editions = listOf(EditionEntity.from(editionMrFox))
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getWorkWithCoversGivesExpectedResultAfterInsertingWork() = runBlocking {
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

    @Test
    fun getWorkWithSubjectsGivesExpectedResultAfterInsertingWork() = runBlocking {
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

    @Test
    fun getAuthorWithWorksGivesExpectedResultAfterInsertingAuthor() = runBlocking {
        libraryDao.insert(authorRoaldDahl)
        val result = libraryDao.getAuthorWithWorks(authorRoaldDahl.id).first()
        val expected = AuthorWithWorks(
            author = AuthorEntity.from(authorRoaldDahl),
            works = listOf(WorkEntity.from(workMrFox))
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getAuthorWithCoversGivesExpectedResultAfterInsertingAuthor() = runBlocking {
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

    @Test
    fun getEditionWithWorksGivesExpectedResultAfterInsertingEdition() = runBlocking {
        libraryDao.insert(editionMrFox)
        val result = libraryDao.getEditionWithWorks(editionMrFox.id).first()
        val expected = EditionWithWorks(
            edition = EditionEntity.from(editionMrFox),
            works = listOf(WorkEntity.from(workMrFox))
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getEditionWithAuthorsGivesExpectedResultAfterInsertingEdition() = runBlocking {
        libraryDao.insert(editionMrFox)
        val result = libraryDao.getEditionWithAuthors(editionMrFox.id).first()
        val expected = EditionWithAuthors(
            edition = EditionEntity.from(editionMrFox),
            authors = listOf(AuthorEntity.from(authorRoaldDahl))
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getEditionWithCoversGivesExpectedResultAfterInsertingEdition() = runBlocking {
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

    @Test
    fun tryingToAccessAWorkAfterDeletingItReturnsNull() = runBlocking {
        libraryDao.insert(workMrFox)
        libraryDao.delete(workMrFox)
        val result = libraryDao.getWork(workMrFox.id).first()
        assertThat(result).isNull()
    }


    @Test
    fun tryingToAccessAnAuthorAfterDeletingItReturnsNull() = runBlocking {
        libraryDao.insert(authorRoaldDahl)
        libraryDao.delete(authorRoaldDahl)
        val result = libraryDao.getAuthor(authorRoaldDahl.id).first()
        assertThat(result).isNull()
    }

    @Test
    fun tryingToAccessCoversAfterDeleteAllCoversReturnsEmpty() = runBlocking {
        libraryDao.insert(editionMrFox)
        val cover = editionMrFox.covers.first()[0]
        libraryDao.delete(cover)
        val result = libraryDao.getCover(cover.id).first()
        assertThat(result).isNull()
    }

    @Test
    fun tryingToAccessAnEditionAfterDeletingItReturnsNull() = runBlocking {
        libraryDao.insert(editionMrFox)
        libraryDao.delete(editionMrFox)
        val result = libraryDao.getEdition(editionMrFox.id).first()
        assertThat(result).isNull()
    }

    @Test
    fun tryingToAccessSubjectsAfterDeleteAllSubjectsReturnsEmpty() = runBlocking {
        libraryDao.insert(workMrFox)
        val subjects = workMrFox.subjects.first()
        subjects.forEach { libraryDao.deleteSubject(it) }
        val result = libraryDao.getWorkWithSubjects(workMrFox.id).first()
        assertThat(result.subjects).isEmpty()
    }
}