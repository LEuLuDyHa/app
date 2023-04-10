package com.github.leuludyha.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.data.TestUtils.author1
import com.github.leuludyha.data.TestUtils.author2
import com.github.leuludyha.data.TestUtils.author3
import com.github.leuludyha.data.TestUtils.author4
import com.github.leuludyha.data.TestUtils.cover1
import com.github.leuludyha.data.TestUtils.cover2
import com.github.leuludyha.data.TestUtils.cover3
import com.github.leuludyha.data.TestUtils.edition1
import com.github.leuludyha.data.TestUtils.edition2
import com.github.leuludyha.data.TestUtils.edition3
import com.github.leuludyha.data.TestUtils.edition4
import com.github.leuludyha.data.TestUtils.populateDatabase
import com.github.leuludyha.data.TestUtils.subject1
import com.github.leuludyha.data.TestUtils.subject2
import com.github.leuludyha.data.TestUtils.subject3
import com.github.leuludyha.data.TestUtils.work1
import com.github.leuludyha.data.TestUtils.work2
import com.github.leuludyha.data.TestUtils.work3
import com.github.leuludyha.data.db.*
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
    fun getWorkGivesExpectedResult() {
        runBlocking { assertThat(libraryDao.getWork(work1.workId).first()).isEqualTo(work1) }
    }

    @Test
    fun getEditionGivesExpectedResult() {
        runBlocking {
            assertThat(libraryDao.getEdition(edition1.editionId).first()).isEqualTo(
                edition1
            )
        }
    }

    @Test
    fun getAuthorGivesExpectedResult() {
        runBlocking {
            assertThat(
                libraryDao.getAuthor(author1.authorId).first()
            ).isEqualTo(author1)
        }
    }

    @Test
    fun getCoverGivesExpectedResult() {
        runBlocking { assertThat(libraryDao.getCover(cover1.coverId).first()).isEqualTo(cover1) }
    }

    @Test
    fun getWorkWithAuthorsGivesExpectedResultWithMultipleAuthors() {
        val expected = WorkWithAuthors(
            work = work1,
            authors = listOf(author1, author2, author3)
        )
        runBlocking {
            val result = libraryDao.getWorkWithAuthors(work1.workId).first()
            assertThat(result.authors).isEqualTo(expected.authors)
            assertThat(result.work).isEqualTo(expected.work)
        }
    }

    @Test
    fun getWorkWithAuthorsGivesExpectedResultWith0Author() {
        val expected = WorkWithAuthors(
            work = work2,
            authors = listOf()
        )
        runBlocking {
            val result = libraryDao.getWorkWithAuthors(work2.workId).first()
            assertThat(result.authors).isEqualTo(expected.authors)
            assertThat(result.work).isEqualTo(expected.work)
        }
    }

    @Test
    fun getWorkWithEditionsGivesExpectedResultWithMultipleEditions() {
        val expected = WorkWithEditions(
            work = work1,
            editions = listOf(edition1, edition2, edition3)
        )
        runBlocking {
            val result = libraryDao.getWorkWithEditions(work1.workId).first()
            assertThat(result.editions).isEqualTo(expected.editions)
            assertThat(result.work).isEqualTo(expected.work)
        }
    }

    @Test
    fun getWorkWithCoversGivesExpectedResultWithMultipleCovers() {
        val expected = WorkWithCovers(
            work = work1,
            covers = listOf(cover1, cover2, cover3)
        )
        runBlocking {
            val result = libraryDao.getWorkWithCovers(work1.workId).first()
            assertThat(result.covers).isEqualTo(expected.covers)
            assertThat(result.work).isEqualTo(expected.work)
        }
    }

    @Test
    fun getWorkWithCoversGivesExpectedResultWith0Cover() {
        val expected = WorkWithCovers(
            work = work2,
            covers = listOf()
        )
        runBlocking {
            val result = libraryDao.getWorkWithCovers(work2.workId).first()
            assertThat(result.covers).isEqualTo(expected.covers)
            assertThat(result.work).isEqualTo(expected.work)
        }
    }

    @Test
    fun getWorkWithSubjectsGivesExpectedResultWithMultipleSubjects() {
        val expected = WorkWithSubjects(
            work = work1,
            subjects = listOf(subject1, subject2, subject3)
        )
        runBlocking {
            val result = libraryDao.getWorkWithSubjects(work1.workId).first()
            assertThat(result.subjects).isEqualTo(expected.subjects)
            assertThat(result.work).isEqualTo(expected.work)
        }
    }

    @Test
    fun getAuthorWithWorksGivesExpectedResultWithMultipleWorks() {
        val expected = AuthorWithWorks(
            author = author1,
            works = listOf(work1, work3)
        )
        runBlocking {
            val result = libraryDao.getAuthorWithWorks(author1.authorId).first()
            assertThat(result.works).isEqualTo(expected.works)
            assertThat(result.author).isEqualTo(expected.author)
        }
    }

    @Test
    fun getAuthorWithCoversGivesExpectedResultWithMultipleWorks() {
        val expected = AuthorWithCovers(
            author = author1,
            covers = listOf(cover1, cover2, cover3)
        )
        runBlocking {
            val result = libraryDao.getAuthorWithCovers(author1.authorId).first()
            assertThat(result.covers).isEqualTo(expected.covers)
            assertThat(result.author).isEqualTo(expected.author)
        }
    }

    @Test
    fun getAuthorWithWorksGivesExpectedResultWith0Works() {
        val expected = AuthorWithWorks(
            author = author4,
            works = listOf()
        )
        runBlocking {
            val result = libraryDao.getAuthorWithWorks(author4.authorId).first()
            assertThat(result.works).isEqualTo(expected.works)
            assertThat(result.author).isEqualTo(expected.author)
        }
    }

    @Test
    fun getEditionWithWorksGivesExpectedResultWithMultipleWorks() {
        val expected = EditionWithWorks(
            edition = edition1,
            works = listOf(work1, work2, work3)
        )
        runBlocking {
            val result = libraryDao.getEditionWithWorks(edition1.editionId).first()
            assertThat(result.works).isEqualTo(expected.works)
            assertThat(result.edition).isEqualTo(expected.edition)
        }
    }

    @Test
    fun getEditionWithWorksGivesExpectedResultWith0Work() {
        val expected = EditionWithWorks(
            edition = edition4,
            works = listOf()
        )
        runBlocking {
            val result = libraryDao.getEditionWithWorks(edition4.editionId).first()
            assertThat(result.works).isEqualTo(expected.works)
            assertThat(result.edition).isEqualTo(expected.edition)
        }
    }

    @Test
    fun getEditionWithAuthorsGivesExpectedResultWithMultipleWorks() {
        val expected = EditionWithAuthors(
            edition = edition1,
            authors = listOf(author1, author2, author3)
        )
        runBlocking {
            val result = libraryDao.getEditionWithAuthors(edition1.editionId).first()
            assertThat(result.authors).isEqualTo(expected.authors)
            assertThat(result.edition).isEqualTo(expected.edition)
        }
    }

    @Test
    fun getEditionWithCoversGivesExpectedResultWithMultipleWorks() {
        val expected = EditionWithCovers(
            edition = edition1,
            covers = listOf(cover1, cover2, cover3)
        )
        runBlocking {
            val result = libraryDao.getEditionWithCovers(edition1.editionId).first()
            assertThat(result.covers).isEqualTo(expected.covers)
            assertThat(result.edition).isEqualTo(expected.edition)
        }
    }

    @Test
    fun tryingToAccessAWorkAfterDeleteAllWorksReturnsNull() {
        runBlocking {
            libraryDao.deleteAllWorks()
            val result = libraryDao.getWork(work1.workId).first()
            assertThat(result).isNull()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessAnAuthorAfterDeleteAllAuthorsReturnsNull() {
        runBlocking {
            libraryDao.deleteAllAuthors()
            val result = libraryDao.getAuthor(author1.authorId).first()
            assertThat(result).isNull()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessCoversAfterDeleteAllCoversReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllCovers()
            val result = libraryDao.getWorkWithCovers(work1.workId).first()
            assertThat(result.covers).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessAnEditionAfterDeleteAllEditionsReturnsNull() {
        runBlocking {
            libraryDao.deleteAllEditions()
            val result = libraryDao.getEdition(edition1.editionId).first()
            assertThat(result).isNull()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessSubjectsAfterDeleteAllSubjectsReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllSubjects()
            val result = libraryDao.getWorkWithSubjects(work1.workId).first()
            assertThat(result.subjects).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessAuthorCoverCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllAuthorCoverCrossRefs()
            val result = libraryDao.getAuthorWithCovers(author1.authorId).first()
            assertThat(result.covers).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessEditionAuthorCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllEditionAuthorCrossRefs()
            val result = libraryDao.getEditionWithAuthors(edition1.editionId).first()
            assertThat(result.authors).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessEditionCoverCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllEditionCoverCrossRefs()
            val result = libraryDao.getEditionWithCovers(edition1.editionId).first()
            assertThat(result.covers).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessWorkAuthorCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllWorkAuthorCrossRefs()
            val result = libraryDao.getWorkWithAuthors(work1.workId).first()
            assertThat(result.authors).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessWorkCoverCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllWorkCoverCrossRefs()
            val result = libraryDao.getWorkWithCovers(work1.workId).first()
            assertThat(result.covers).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessWorkEditionCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllWorkEditionCrossRefs()
            val result = libraryDao.getWorkWithEditions(work1.workId).first()
            assertThat(result.editions).isEmpty()
        }

        populateDatabase(libraryDao)
    }

    @Test
    fun tryingToAccessWorkSubjectCrossRefAfterDeleteAllReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllWorkSubjectCrossRefs()
            val result = libraryDao.getWorkWithSubjects(work1.workId).first()
            assertThat(result.subjects).isEmpty()
        }

        populateDatabase(libraryDao)
    }
}