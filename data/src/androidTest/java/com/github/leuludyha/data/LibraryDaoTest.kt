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

    private val work1 = Work("work1", "MyWork1")
    private val work2 = Work("work2", "MyWork2")
    private val work3 = Work("work3", "MyWork3")
    private val author1 = Author("author1", null, "Author1", null, null)
    private val author2 = Author("author2", null, "Author2", null, null)
    private val author3 = Author("author3", null, "Author3", null, null)
    private val author4 = Author("author4", null, "Author4", null, null)
    private val edition1 = Edition("edition1", "MyEdition1")
    private val edition2 = Edition("edition2", "MyEdition2")
    private val edition3 = Edition("edition3", "MyEdition3")
    private val edition4 = Edition("edition4", "MyEdition4")
    private val cover1 = Cover(1, "coverUrl1")
    private val cover2 = Cover(2, "coverUrl2")
    private val cover3 = Cover(3, "coverUrl3")

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
        runBlocking { assertThat(libraryDao.getWorkWithAuthors(work1.workId).first()).isEqualTo(expected) }
    }

    @Test
    fun getWorkWithAuthorsGivesExpectedResultWith0Author() {
        val expected = WorkWithAuthors(
            work = work2,
            authors = listOf()
        )
        runBlocking { assertThat(libraryDao.getWorkWithAuthors(work2.workId).first()).isEqualTo(expected) }
    }

    @Test
    fun getWorkWithEditionsGivesExpectedResultWithMultipleEditions() {
        val expected = WorkWithEditions(
            work = work1,
            editions = listOf(edition1, edition2, edition3)
        )
        runBlocking { assertThat(libraryDao.getWorkWithEditions(work1.workId).first()).isEqualTo(expected) }
    }

    @Test
    fun getWorkWithEditionsGivesExpectedResultWith0Edition() {
        val expected = WorkWithEditions(
            work = work2,
            editions = listOf()
        )
        runBlocking { assertThat(libraryDao.getWorkWithEditions(work2.workId).first()).isEqualTo(expected) }
    }

    @Test
    fun getWorkWithCoversGivesExpectedResultWithMultipleCovers() {
        val expected = WorkWithCovers(
            work = work1,
            covers = listOf(cover1, cover2, cover3)
        )
        runBlocking { assertThat(libraryDao.getWorkWithCovers(work1.workId).first()).isEqualTo(expected) }
    }

    @Test
    fun getWorkWithCoversGivesExpectedResultWith0Cover() {
        val expected = WorkWithCovers(
            work = work2,
            covers = listOf()
        )
        runBlocking { assertThat(libraryDao.getWorkWithCovers(work2.workId).first()).isEqualTo(expected) }
    }

    @Test
    fun getAuthorWithWorksGivesExpectedResultWithMultipleWorks() {
        val expected = AuthorWithWorks(
            author = author1,
            works = listOf(work1, work3)
        )
        runBlocking { assertThat(libraryDao.getAuthorWithWorks(author1.authorId).first()).isEqualTo(expected) }
    }

    @Test
    fun getAuthorWithWorksGivesExpectedResultWith0Works() {
        val expected = AuthorWithWorks(
            author = author4,
            works = listOf()
        )
        runBlocking { assertThat(libraryDao.getAuthorWithWorks(author4.authorId).first()).isEqualTo(expected) }
    }

    @Test
    fun getEditionWithWorksGivesExpectedResultWithMultipleWorks() {
        val expected = EditionWithWorks(
            edition = edition1,
            works = listOf(work1, work3)
        )
        runBlocking { assertThat(libraryDao.getEditionWithWorks(edition1.editionId).first()).isEqualTo(expected) }
    }

    @Test
    fun getEditionWithWorksGivesExpectedResultWith0Work() {
        val expected = EditionWithWorks(
            edition = edition4,
            works = listOf()
        )
        runBlocking { assertThat(libraryDao.getEditionWithWorks(edition4.editionId).first()).isEqualTo(expected) }
    }

    @Test
    fun getEditionWithAuthorsGivesExpectedResultWithMultipleWorks() {
        val expected = EditionWithAuthors(
            edition = edition1,
            authors = listOf(author1, author2, author3)
        )
        runBlocking { assertThat(libraryDao.getEditionWithAuthors(edition1.editionId).first()).isEqualTo(expected) }
    }

    @Test
    fun getEditionWithCoversGivesExpectedResultWithMultipleWorks() {
        val expected = EditionWithCovers(
            edition = edition1,
            covers = listOf(cover1, cover2, cover3)
        )
        runBlocking { assertThat(libraryDao.getEditionWithCovers(edition1.editionId).first()).isEqualTo(expected) }
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}