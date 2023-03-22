package com.github.leuludyha.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.leuludyha.data.db.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LibraryDaoTest {
    private lateinit var libraryDao: LibraryDao
    private lateinit var db: LibraryDatabase

    private val work1 = WorkEntity("work1", "MyWork1")
    private val work2 = WorkEntity("work2", "MyWork2")
    private val work3 = WorkEntity("work3", "MyWork3")
    private val author1 = AuthorEntity("author1", null, "Author1", null, null)
    private val author2 = AuthorEntity("author2", null, "Author2", null, null)
    private val author3 = AuthorEntity("author3", null, "Author3", null, null)
    private val author4 = AuthorEntity("author4", null, "Author4", null, null)
    private val edition1 = EditionEntity("edition1", "MyEdition1")
    private val edition2 = EditionEntity("edition2", "MyEdition2")
    private val edition3 = EditionEntity("edition3", "MyEdition3")
    private val edition4 = EditionEntity("edition4", "MyEdition4")
    private val cover1 = CoverEntity(1)
    private val cover2 = CoverEntity(2)
    private val cover3 = CoverEntity(3)

    @Before
    fun createDatabase() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LibraryDatabase::class.java
        ).allowMainThreadQueries().build()

        libraryDao = db.libraryDao()

        populateDatabase()
    }

    private fun populateDatabase() {
        runBlocking {
            libraryDao.insert(work1)
            libraryDao.insert(work2)
            libraryDao.insert(work3)

            libraryDao.insert(author1)
            libraryDao.insert(author2)
            libraryDao.insert(author3)
            libraryDao.insert(author4)

            libraryDao.insert(edition1)
            libraryDao.insert(edition2)
            libraryDao.insert(edition3)
            libraryDao.insert(edition4)

            libraryDao.insert(cover1)
            libraryDao.insert(cover2)
            libraryDao.insert(cover3)

            libraryDao.insert(WorkAuthorCrossRef(work1.workId, author1.authorId))
            libraryDao.insert(WorkAuthorCrossRef(work1.workId, author2.authorId))
            libraryDao.insert(WorkAuthorCrossRef(work1.workId, author3.authorId))
            libraryDao.insert(WorkAuthorCrossRef(work3.workId, author1.authorId))

            libraryDao.insert(WorkEditionCrossRef(work1.workId, edition1.editionId))
            libraryDao.insert(WorkEditionCrossRef(work1.workId, edition2.editionId))
            libraryDao.insert(WorkEditionCrossRef(work1.workId, edition3.editionId))
            libraryDao.insert(WorkEditionCrossRef(work3.workId, edition1.editionId))

            libraryDao.insert(WorkCoverCrossRef(work1.workId, cover1.coverId))
            libraryDao.insert(WorkCoverCrossRef(work1.workId, cover2.coverId))
            libraryDao.insert(WorkCoverCrossRef(work1.workId, cover3.coverId))

            libraryDao.insert(EditionAuthorCrossRef(edition1.editionId, author1.authorId))
            libraryDao.insert(EditionAuthorCrossRef(edition1.editionId, author2.authorId))
            libraryDao.insert(EditionAuthorCrossRef(edition1.editionId, author3.authorId))

            libraryDao.insert(EditionCoverCrossRef(edition1.editionId, cover1.coverId))
            libraryDao.insert(EditionCoverCrossRef(edition1.editionId, cover2.coverId))
            libraryDao.insert(EditionCoverCrossRef(edition1.editionId, cover3.coverId))

            libraryDao.insert(AuthorCoverCrossRef(author1.authorId, cover1.coverId))
            libraryDao.insert(AuthorCoverCrossRef(author1.authorId, cover2.coverId))
            libraryDao.insert(AuthorCoverCrossRef(author4.authorId, cover3.coverId))
        }
    }

    @Test
    fun getWorkGivesExpectedResult() {
        runBlocking { assertThat(libraryDao.getWork(work1.workId).first()).isEqualTo(work1) }
    }

    @Test
    fun getEditionGivesExpectedResult() {
        runBlocking { assertThat(libraryDao.getEdition(edition1.editionId).first()).isEqualTo(edition1) }
    }

    @Test
    fun getAuthorGivesExpectedResult() {
        runBlocking { assertThat(libraryDao.getAuthor(author1.authorId).first()).isEqualTo(author1) }
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
    fun getWorkWithEditionsGivesExpectedResultWith0Edition() {
        val expected = WorkWithEditions(
            work = work2,
            editions = listOf()
        )
        runBlocking {
            val result = libraryDao.getWorkWithEditions(work2.workId).first()
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
            covers = listOf(cover1, cover2)
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
            works = listOf(work1, work3)
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

        populateDatabase()
    }

    @Test
    fun tryingToAccessAnAuthorAfterDeleteAllAuthorsReturnsNull() {
        runBlocking {
            libraryDao.deleteAllAuthors()
            val result = libraryDao.getAuthor(author1.authorId).first()
            assertThat(result).isNull()
        }

        populateDatabase()
    }

    @Test
    fun tryingToAccessCoversAfterDeleteAllCoversReturnsEmpty() {
        runBlocking {
            libraryDao.deleteAllCovers()
            val result = libraryDao.getWorkWithCovers(work1.workId).first()
            assertThat(result.covers).isEmpty()
        }

        populateDatabase()
    }

    @Test
    fun tryingToAccessAnEditionAfterDeleteAllEditionsReturnsNull() {
        runBlocking {
            libraryDao.deleteAllEditions()
            val result = libraryDao.getEdition(edition1.editionId).first()
            assertThat(result).isNull()
        }

        populateDatabase()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}